package com.qiniu.lab.service.fop;

import java.util.List;

public class PipeFopCmd implements FopCmd {
	private List<FopCmd> fopCmdList;

	public void addFopCmd(FopCmd fopCmd) {
		this.fopCmdList.add(fopCmd);
	}

	public void removeFopCmd(FopCmd fopCmd) {
		this.fopCmdList.remove(fopCmd);
	}

	@Override
	public String toFopCmd() {
		StringBuilder sb = new StringBuilder();
		int fopCmdCount = this.fopCmdList.size();
		for (int i = 0; i < fopCmdCount; i++) {
			sb.append(this.fopCmdList.get(i).toFopCmd());
			if (i < fopCmdCount - 1) {
				sb.append("|");
			}
		}
		return sb.toString();
	}

}
