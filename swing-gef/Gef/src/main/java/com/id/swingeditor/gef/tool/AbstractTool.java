package com.id.swingeditor.gef.tool;

import java.awt.Point;
import java.awt.event.MouseEvent;

import com.id.swingeditor.gef.EditDomain;

/**
 * @author idanilov
 *
 */
public class AbstractTool
		implements XTool {

	protected EditDomain domain;
	protected ToolState state;
	protected Point start;
	protected MouseEvent mouseEvent;

	public ToolState getState() {
		return state;
	}

	protected void setState(ToolState state) {
		this.state = state;
	}

	/**
	 * Returns <code>true</code> if the give state transition succeeds. This is a "test and
	 * set" operation, where the tool is tested to be in the specified start state, and if so,
	 * is set to the given end state.  The method returns the result of the first test.
	 * @param start the start state being tested
	 * @param end the end state
	 * @return <code>true</code> if the state transition is successful
	 */
	protected boolean stateTransition(ToolState start, ToolState end) {
		if (getState() == start) {
			setState(end);
			return true;
		}
		return false;
	}

	public void setEditDomain(EditDomain domain) {
		this.domain = domain;
	}

	public Point getStartLocation() {
		return start;
	}

	public void setStartLocation(Point start) {
		this.start = start;
	}

	public void activate() {
	}

	public void deactivate() {
	}

	private void setMouse(MouseEvent me) {
		mouseEvent = me;
	}

	public MouseEvent getMouse() {
		return mouseEvent;
	}

	public void mouseClicked(MouseEvent me) {
		setMouse(me);
	}

	public void mouseDragged(MouseEvent me) {
		setMouse(me);
	}

	public void mouseEntered(MouseEvent me) {
		setMouse(me);
	}

	public void mouseExited(MouseEvent me) {
		setMouse(me);
	}

	public void mouseMoved(MouseEvent me) {
		setMouse(me);
	}

	public void mousePressed(MouseEvent me) {
		setMouse(me);
		setStartLocation(me.getPoint());
	}

	public void mouseReleased(MouseEvent me) {
		setMouse(me);
	}

}
