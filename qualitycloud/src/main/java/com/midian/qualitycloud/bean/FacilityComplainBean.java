package com.midian.qualitycloud.bean;

import java.util.ArrayList;

import com.google.gson.JsonSyntaxException;
import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * 5.3.曝光台
 * 
 * @author Administrator
 * 
 */
public class FacilityComplainBean extends NetResult {
	public static FacilityComplainBean parse(String json) throws AppException {
		FacilityComplainBean res = new FacilityComplainBean();
		try {
			res = gson.fromJson(json, FacilityComplainBean.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			throw AppException.json(e);
		}
		return res;

	}

	private Content content;

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	// public class Content extends NetResult{
	// private String punished;
	// private String trapped;
	//
	// public String getPunished() {
	// return punished;
	// }
	//
	// public void setPunished(String punished) {
	// this.punished = punished;
	// }
	//
	// public String getTrapped() {
	// return trapped;
	// }
	//
	// public void setTrapped(String trapped) {
	// this.trapped = trapped;
	// }
	// }
	public class Content extends NetResult {
		private PunishedFacility punishedFacility;
		private TrappedFacility trappedFacility;

		public PunishedFacility getPunishedFacility() {
			return punishedFacility;
		}

		public void setPunishedFacility(PunishedFacility punishedFacility) {
			this.punishedFacility = punishedFacility;
		}

		public TrappedFacility getTrappedFacility() {
			return trappedFacility;
		}

		public void setTrappedFacility(TrappedFacility trappedFacility) {
			this.trappedFacility = trappedFacility;
		}
	}

	private class PunishedFacility extends NetResult {
		private ArrayList<StatsFacility> stats;

		public ArrayList<StatsFacility> getStats() {
			return stats;
		}

		public void setStats(ArrayList<StatsFacility> stats) {
			this.stats = stats;
		}
	}

	private class TrappedFacility extends NetResult {
		private ArrayList<StatsFacility> stats;

		public ArrayList<StatsFacility> getStats() {
			return stats;
		}

		public void setStats(ArrayList<StatsFacility> stats) {
			this.stats = stats;
		}
	}

	private class StatsFacility extends NetResult {
		private String year;// 年度
		private String count;// 次数

		public String getYear() {
			return year;
		}

		public void setYear(String year) {
			this.year = year;
		}

		public String getCount() {
			return count;
		}

		public void setCount(String count) {
			this.count = count;
		}
	}
}
