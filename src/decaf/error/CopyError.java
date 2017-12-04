package decaf.error;

import decaf.Location;

/**
 * use error about super expr
 * PA2
 */
public class CopyError extends DecafError {
	public static final int NOT_CLASS = 0;
	public static final int DESTINATION_NOT_CONSISTENT = NOT_CLASS + 1;

	private int type;
	private String givenType, source, destination;

	public CopyError(Location location, int type, String givenType, String source, String destination) {
		super(location);
		this.type = type;
		this.givenType = givenType;
		this.source = source;
		this.destination = destination;
	}

	@Override
	protected String getErrMsg() {
		switch (type) {
			case NOT_CLASS:
				return "expected class type for copy expr but " + givenType + " given";
			case DESTINATION_NOT_CONSISTENT:
				return "For copy expr, the source " + source + " and the destination " + destination + " are not same";
		}
		return "copy error";
	}

}
