package be.blop.libcap.linux;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface LinuxLibrary extends Library {
	LinuxLibrary INSTANCE = (LinuxLibrary) Native.loadLibrary(LinuxLibrary.class);

	/**
	 * Operations on a process
	 */
	int prctl(int option, long arg2, long arg3, long arg4, long arg5);
}