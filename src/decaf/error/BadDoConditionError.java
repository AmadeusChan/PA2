package decaf.error;

import decaf.Location;

/**
 * example：The condition of Do Stmt requestd type bool but int given<br>
 * PA2
 */
public class BadDoConditionError extends DecafError {

	private String givenType;

	public BadDoConditionError(Location location, String givenType) {
		super(location);
		this.givenType = givenType;
	}

	@Override
	protected String getErrMsg() {
		return "The condition of Do Stmt requestd type bool but " + givenType + " given";
	}

}
