package be.blop.libcap.libcap;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Structure;

public class Cap extends Structure {
	public static class CapHeader extends Structure {
		public int version;
		public int pid;

		@Override
		protected List getFieldOrder() {
			return Arrays.asList("version", "pid");
		}
	}

	public static class CapData extends Structure {
		public int effective;
		public int permitted;
		public int inheritable;

		@Override
		protected List getFieldOrder() {
			return Arrays.asList("effective", "permitted", "inheritable");
		}
	}

	public CapHeader head;
	public CapData set;

	@Override
	protected List getFieldOrder() {
		return Arrays.asList("head", "set");
	}
}