package be.blop.libcap.libcap;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.NativeLongByReference;

public interface CapLibrary extends Library {
	CapLibrary INSTANCE = (CapLibrary) Native.loadLibrary("libcap", CapLibrary.class);

	/**
	 * libcap/cap_alloc.c
	 */
	Cap cap_dup(Cap cap_p);
	int cap_free(Cap cap_p);
	Cap cap_init();

	/**
	 * libcap/cap_flag.c
	 */
	int cap_get_flag(Cap cap_p, int cap, int flag, Pointer value_p);
	int cap_set_flag(Cap cap_p, int flag, int ncap, int[] caps, int value);
	int cap_clear(Cap cap_p);
	int cap_clear_flag(Cap cap_p, int flag);

	/**
	 * libcap/cap_file.c
	 */
	Cap cap_get_fd(int fd);
	Cap cap_get_file(Pointer path_p);
	int cap_set_fd(int fd, Cap cap);
	int cap_set_file(Pointer path_p, Cap cap_p);

	/**
	 * libcap/cap_proc.c
	 */
	Cap cap_get_proc();
	Cap cap_get_pid(int pid);
	int cap_set_proc(Cap cap_p);
	int cap_get_bound(int cap_value_t);
	int cap_drop_bound(int cap_value_t);

	/**
	 * libcap/cap_extint.c
	 */
	NativeLong cap_size(Cap cap_p);
	NativeLong cap_copy_ext(Pointer ext_p, Cap cap_p, NativeLong size);
	Cap cap_copy_int(Pointer ext_p);

	/**
	 * libcap/cap_text.c
	 */
	Cap cap_from_text(String buf_p);
	String cap_to_text(Cap cap, NativeLongByReference length_p);
	int cap_from_name(String name, Pointer cap_p);
	String cap_to_name(int cap);
	int cap_compare(Cap cap_a, Cap cap_b);
}