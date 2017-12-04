package decaf.error;

import decaf.Location;

/**
 * example：incompatible case expr: bool given, but int expected<br>
 * PA2
 */
public class BadCaseExprError extends DecafError {

	public static int BadCondition = 0;
	public static int CiNotUnique = BadCondition + 1;
	public static int EiNotConsistent = CiNotUnique + 1;

	private String count;

	private String typeGiven, typeExpected;
	private int type;

	public BadCaseExprError(int type, Location location, String typeGiven, String typeExpected) {
		super(location);
		this.type = type;
		this.typeExpected = typeExpected;
		this.typeGiven = typeGiven;
	}

	@Override
	protected String getErrMsg() {
		if (type == BadCondition) {
			return "incompatible case expr: " + typeGiven + " given, but " + typeExpected + " expected";
		} else if (type == CiNotUnique) {
			return "condition is not unique";
		} else {
			return "type: " + typeGiven + " is different with other expr's type " + typeExpected;
		}
		/*
		return "incompatible argument " + count + ": " + type
				+ " given, complex expected";
		*/
	}

}
