package calendar.model;

/**
 * @author idanilov
 *
 */
public class CalendarTask {

	private CalendarRule recurrentRule;
	private Object target;

	public void setTarget(final Object target) {
		this.target = target;
	}

	public Object getTarget() {
		return target;
	}

	public void setRecurrentRule(final CalendarRule recurrentRule) {
		this.recurrentRule = recurrentRule;
	}

	public CalendarRule getRecurrentRule() {
		return recurrentRule;
	}

	/**
	 * User friendly string of task.
	 * @return Value to be shown in UI  
	 */
	public String getLabel() {
		return target != null ? target.toString() : "";
	}

}
