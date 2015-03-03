package com.qiniu.lab.service.fop;

import java.util.List;

public class FopUtil {

	public static String fopsToString(List<FopCmd> fopCmdList) {
		StringBuilder sb = new StringBuilder();
		for (FopCmd fopCmd : fopCmdList) {
			sb.append(fopCmd.toFopCmd()).append(";");
		}
		return sb.toString();
	}

	public static String fopsToString(FopCmd fopCmd) {
		return fopCmd.toFopCmd();
	}
}
