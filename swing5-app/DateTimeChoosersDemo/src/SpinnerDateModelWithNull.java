import javax.swing.SpinnerDateModel;

/**
 * @author idanilov
 *
 */
public class SpinnerDateModelWithNull extends SpinnerDateModel {

	private boolean isNullValue;

	public SpinnerDateModelWithNull() {
		super();
	}

	public void setValue(Object value) {
		if (value == null) {
			if (!isNullValue) {
				isNullValue = true;
				fireStateChanged();
			}
		} else {
			isNullValue = false;
			super.setValue(value);
		}
	}

	public Object getValue() {
		if (isNullValue) {
			return null;
		}
		return super.getValue();
	}

}
