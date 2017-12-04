package decaf.error;

import decaf.Location;

/**
 * use error about super expr
 * PA2
 */
public class SuperError extends DecafError {
	public static final int USED_IN_STATIC_FUN = 0;
	public static final int ACCESS_VAR_MEM = USED_IN_STATIC_FUN + 1;
	public static final int NO_PARENT_CLASS = ACCESS_VAR_MEM + 1;

	private int type;
	private String methodName, className;

	public SuperError(Location location, int type, String methodName, String className) {
		super(location);
		this.type = type;
		this.methodName = methodName;
		this.className = className;
	}

	@Override
	protected String getErrMsg() {
		switch (type) {
			case USED_IN_STATIC_FUN: 
				return "can not use super in static function";
			case ACCESS_VAR_MEM:
				return "super.member_var is not supported";
			case NO_PARENT_CLASS:
				return "no parent class exist for " + className;
		}
		return "super error";
	}

}
