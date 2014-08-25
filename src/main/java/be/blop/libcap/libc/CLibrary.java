package be.blop.libcap.libc;

import java.nio.IntBuffer;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;

public interface CLibrary extends Library {
	CLibrary INSTANCE = (CLibrary) Native.loadLibrary("c", CLibrary.class);

	/**
	 * Get/Set UID
	 */
	int getuid();
	int setuid(int uid);

	/**
	 * Get/Set GID
	 */
	int getgid();
	int setgid(int gid);

	Passwd getpwnam(String login);

	/**
	 * Set file creation mode mask
	 */
	int umask(int mask);

	/**
	 * Get/Set groups
	 */
	int getgroups(int size, IntBuffer gids);
	int setgroups(int size, IntBuffer gids);
	int getgrouplist(String user, int group, IntBuffer groups, IntByReference ngroups);
}