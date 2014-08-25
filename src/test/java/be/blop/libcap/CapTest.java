package be.blop.libcap;

import java.net.ServerSocket;
import java.nio.IntBuffer;

import be.blop.libcap.libc.CLibrary;
import be.blop.libcap.libcap.Cap;
import be.blop.libcap.libcap.CapLibrary;
import be.blop.libcap.linux.LinuxLibrary;
import be.blop.libcap.linux.PrctlOptions;
import be.blop.libcap.linux.SecureBits;
import com.sun.jna.ptr.NativeLongByReference;

import static be.blop.libcap.libcap.Capabilities.CAP_NET_BIND_SERVICE;
import static be.blop.libcap.libcap.SetIdentifiers.CAP_EFFECTIVE;
import static be.blop.libcap.libcap.SetIdentifiers.CAP_PERMITTED;
import static be.blop.libcap.libcap.States.CAP_SET;

public class CapTest {
	public static void main(String[] args) throws Exception {
		// Configure uid/gid/groups/caps
		configureUserWithNetBind();

		// Try to open a socket
		Thread subThread = new Thread(() -> {
			System.out.println("Trying to listen to port < 1024 ..");
			try {
				new ServerSocket(80);
				System.out.println("Success !");
				Thread.sleep(30000);
			} catch (Exception e) {
				System.err.println("Exception");
				e.printStackTrace();
			}
		});
		subThread.start();
		subThread.join();
	}

	public static void configureUserWithNetBind() {
		try {
			// Parameters
			int uid = 1000;
			int gid = 1000;
			int[] groups = {1000};
			int[] capabilities = {CAP_NET_BIND_SERVICE};

			// Get current process capabilities
			Cap cap = CapLibrary.INSTANCE.cap_get_proc();
			System.out.println("Current process capabilities : " + CapLibrary.INSTANCE.cap_to_text(cap, new NativeLongByReference()));
			CapLibrary.INSTANCE.cap_free(cap);

			// Build new capabilities
			cap = CapLibrary.INSTANCE.cap_init();
			CapLibrary.INSTANCE.cap_set_flag(cap, CAP_PERMITTED, capabilities.length, capabilities, CAP_SET);
			int cap_set_proc = CapLibrary.INSTANCE.cap_set_proc(cap);
			System.out.println("Modifying capabilities to (" + CapLibrary.INSTANCE.cap_to_text(cap, new NativeLongByReference()) + ") = " + cap_set_proc);
			CapLibrary.INSTANCE.cap_free(cap);

			// Ask to keep capabilities
			LinuxLibrary.INSTANCE.prctl(PrctlOptions.PR_SET_KEEPCAPS, 1, 0, 0, 0);

			// Setup secure bits
			int secure_bits = LinuxLibrary.INSTANCE.prctl(PrctlOptions.PR_GET_SECUREBITS, 0, 0, 0, 0);
			secure_bits |= SecureBits.SECBIT_KEEP_CAPS | SecureBits.SECBIT_NO_SETUID_FIXUP;
			LinuxLibrary.INSTANCE.prctl(PrctlOptions.PR_SET_SECUREBITS, secure_bits, 0, 0, 0);

			// Set GID/UID
			CLibrary.INSTANCE.setgroups(1, IntBuffer.wrap(groups));
			CLibrary.INSTANCE.setgid(gid);
			CLibrary.INSTANCE.setuid(uid);

			// Get current process capabilities
			cap = CapLibrary.INSTANCE.cap_get_proc();
			System.out.println("After GID/UID capabilities : " + CapLibrary.INSTANCE.cap_to_text(cap, new NativeLongByReference()));

			// Restoring effective capabilities
			CapLibrary.INSTANCE.cap_set_flag(cap, CAP_EFFECTIVE, capabilities.length, capabilities, CAP_SET);
			cap_set_proc = CapLibrary.INSTANCE.cap_set_proc(cap);
			System.out.println("Restoring capabilities to (" + CapLibrary.INSTANCE.cap_to_text(cap, new NativeLongByReference()) + ") = " + cap_set_proc);
			CapLibrary.INSTANCE.cap_free(cap);
		} catch (Exception e) {
			System.err.println("Exception");
			e.printStackTrace();
		}
	}
}