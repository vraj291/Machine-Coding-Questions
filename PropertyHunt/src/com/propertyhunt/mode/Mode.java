package com.propertyhunt.mode;

import com.propertyhunt.commands.CommandDriver;

public abstract class Mode {
	
	protected CommandDriver cmd;
	
	public Mode(CommandDriver cmd) {
		this.cmd = cmd;
	}
	
	public abstract void run();
	
}
