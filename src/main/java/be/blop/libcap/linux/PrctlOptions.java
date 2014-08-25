package be.blop.libcap.linux;

public interface PrctlOptions {
	/**
	 * Values to pass as first argument to prctl()
	 */
	int PR_SET_PDEATHSIG = 1;			// Second arg is a signal
	int PR_GET_PDEATHSIG = 2;			// Second arg is a ptr to return the signal

	/**
	 * Get/set current->mm->dumpable
	 */
	int PR_GET_DUMPABLE = 3;
	int PR_SET_DUMPABLE = 4;

	/**
	 * Get/set unaligned access control bits (if meaningful)
	 */
	int PR_GET_UNALIGN = 5;
	int PR_SET_UNALIGN = 6;
	int PR_UNALIGN_NOPRINT = 1;			// silently fix up unaligned user accesses
	int PR_UNALIGN_SIGBUS = 2;			// generate SIGBUS on unaligned user access

	/**
	 * Get/set whether or not to drop capabilities on setuid() away from
	 * uid 0 (as per security/commoncap.c)
	 */
	int PR_GET_KEEPCAPS = 7;
	int PR_SET_KEEPCAPS = 8;

	/**
	 * Get/set floating-point emulation control bits (if meaningful)
	 */
	int PR_GET_FPEMU = 9;
	int PR_SET_FPEMU = 10;
	int PR_FPEMU_NOPRINT = 1;			// silently emulate fp operations accesses
	int PR_FPEMU_SIGFPE = 2;			// don't emulate fp operations, send SIGFPE instead

	/**
	 * Get/set floating-point exception mode (if meaningful)
	 */
	int PR_GET_FPEXC = 11;
	int PR_SET_FPEXC = 12;
	int PR_FP_EXC_SW_ENABLE = 0x80;		// Use FPEXC for FP exception enables
	int PR_FP_EXC_DIV = 0x010000;		// floating point divide by zero
	int PR_FP_EXC_OVF = 0x020000;		// floating point overflow
	int PR_FP_EXC_UND = 0x040000;		// floating point underflow
	int PR_FP_EXC_RES = 0x080000;		// floating point inexact result
	int PR_FP_EXC_INV = 0x100000;		// floating point invalid operation
	int PR_FP_EXC_DISABLED = 0;			// FP exceptions disabled
	int PR_FP_EXC_NONRECOV = 1;			// async non-recoverable exc. mode
	int PR_FP_EXC_ASYNC = 2;			// async recoverable exception mode
	int PR_FP_EXC_PRECISE = 3;			// precise exception mode

	/**
	 * Get/set whether we use statistical process timing or accurate timestamp
	 * based process timing
	 */
	int PR_GET_TIMING = 13;
	int PR_SET_TIMING = 14;
	int PR_TIMING_STATISTICAL = 0;		// Normal, traditional, statistical process timing
	int PR_TIMING_TIMESTAMP = 1;		// Accurate timestamp based process timing

	int PR_SET_NAME = 15;				// Set process name
	int PR_GET_NAME = 16;				// Get process name

	/**
	 * Get/set process endian
	 */
	int PR_GET_ENDIAN = 19;
	int PR_SET_ENDIAN = 20;
	int PR_ENDIAN_BIG = 0;
	int PR_ENDIAN_LITTLE = 1;			// True little endian mode
	int PR_ENDIAN_PPC_LITTLE = 2;		// "PowerPC" pseudo little endian

	/**
	 * Get/set process seccomp mode
	 */
	int PR_GET_SECCOMP = 21;
	int PR_SET_SECCOMP = 22;

	/**
	 * Get/set the capability bounding set (as per security/commoncap.c)
	 */
	int PR_CAPBSET_READ = 23;
	int PR_CAPBSET_DROP = 24;

	/**
	 * Get/set the process' ability to use the timestamp counter instruction
	 */
	int PR_GET_TSC = 25;
	int PR_SET_TSC = 26;
	int PR_TSC_ENABLE = 1;				// allow the use of the timestamp counter
	int PR_TSC_SIGSEGV = 2;		 		// throw a SIGSEGV instead of reading the TSC

	/**
	 * Get/set securebits (as per security/commoncap.c)
	 */
	int PR_GET_SECUREBITS = 27;
	int PR_SET_SECUREBITS = 28;

	/**
	 * Get/set the timerslack as used by poll/select/nanosleep
	 * A value of 0 means "use default"
	 */
	int PR_SET_TIMERSLACK = 29;
	int PR_GET_TIMERSLACK = 30;

	int PR_TASK_PERF_EVENTS_DISABLE = 31;
	int PR_TASK_PERF_EVENTS_ENABLE = 32;

	/**
	 * Set early/late kill mode for hwpoison memory corruption.
	 * This influences when the process gets killed on a memory corruption.
	 */
	int PR_MCE_KILL = 33;
	int PR_MCE_KILL_CLEAR = 0;
	int PR_MCE_KILL_SET = 1;

	int PR_MCE_KILL_LATE = 0;
	int PR_MCE_KILL_EARLY = 1;
	int PR_MCE_KILL_DEFAULT = 2;

	int PR_MCE_KILL_GET = 34;

	/**
	 * Tune up process memory map specifics.
	 */
	int PR_SET_MM = 35;
	int PR_SET_MM_START_CODE = 1;
	int PR_SET_MM_END_CODE = 2;
	int PR_SET_MM_START_DATA = 3;
	int PR_SET_MM_END_DATA = 4;
	int PR_SET_MM_START_STACK = 5;
	int PR_SET_MM_START_BRK = 6;
	int PR_SET_MM_BRK = 7;
	int PR_SET_MM_ARG_START = 8;
	int PR_SET_MM_ARG_END = 9;
	int PR_SET_MM_ENV_START = 10;
	int PR_SET_MM_ENV_END = 11;
	int PR_SET_MM_AUXV = 12;
	int PR_SET_MM_EXE_FILE = 13;

	/**
	 * Set specific pid that is allowed to ptrace the current task.
	 * A value of 0 mean "no process".
	 */
	long PR_SET_PTRACER = 0x59616d61;
	long PR_SET_PTRACER_ANY = Long.MAX_VALUE - 1;

	int PR_SET_CHILD_SUBREAPER = 36;
	int PR_GET_CHILD_SUBREAPER = 37;

	/**
	 * If no_new_privs is set, then operations that grant new privileges (i.e.
	 * execve) will either fail or not grant them.	This affects suid/sgid,
	 * file capabilities, and LSMs.
	 * <p>
	 * Operations that merely manipulate or drop existing privileges (setresuid,
	 * capset, etc.) will still work.	Drop those privileges if you want them gone.
	 * <p>
	 * Changing LSM security domain is considered a new privilege.	So, for example,
	 * asking selinux for a specific new context (e.g. with runcon) will result
	 * in execve returning -EPERM.
	 * <p>
	 * See Documentation/prctl/no_new_privs.txt for more details.
	 */
	int PR_SET_NO_NEW_PRIVS = 38;
	int PR_GET_NO_NEW_PRIVS = 39;

	int PR_GET_TID_ADDRESS = 40;
}