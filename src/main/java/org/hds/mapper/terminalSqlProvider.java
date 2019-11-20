package org.hds.mapper;

import org.apache.ibatis.jdbc.SQL;
import org.hds.model.terminal;

public class terminalSqlProvider {

	public String insertSelective(terminal record) {
		SQL sql = new SQL();
		sql.INSERT_INTO("t_terminals");

		if (record.getDtukey() != null) {
			sql.VALUES("DtuKey", "#{dtukey,jdbcType=VARCHAR}");
		}

		if (record.getName() != null) {
			sql.VALUES("Name", "#{name,jdbcType=VARCHAR}");
		}

		if (record.getGroupid() != null) {
			sql.VALUES("GroupID", "#{groupid,jdbcType=INTEGER}");
		}

		if (record.getLedId() != null) {
			sql.VALUES("LED_ID", "#{ledId,jdbcType=VARCHAR}");
		}

		if (record.getSimno() != null) {
			sql.VALUES("SIMNo", "#{simno,jdbcType=VARCHAR}");
		}

		if (record.getTaxitype() != null) {
			sql.VALUES("TaxiType", "#{taxitype,jdbcType=VARCHAR}");
		}

		if (record.getComanyname() != null) {
			sql.VALUES("ComanyName", "#{comanyname,jdbcType=VARCHAR}");
		}

		if (record.getDriver() != null) {
			sql.VALUES("driver", "#{driver,jdbcType=VARCHAR}");
		}

		if (record.getTel() != null) {
			sql.VALUES("tel", "#{tel,jdbcType=VARCHAR}");
		}

		if (record.getPapertype() != null) {
			sql.VALUES("paperType", "#{papertype,jdbcType=VARCHAR}");
		}

		if (record.getPaperno() != null) {
			sql.VALUES("paperNo", "#{paperno,jdbcType=VARCHAR}");
		}

		if (record.getRemark() != null) {
			sql.VALUES("remark", "#{remark,jdbcType=VARCHAR}");
		}

		if (record.getDelindex() != null) {
			sql.VALUES("DelIndex", "#{delindex,jdbcType=INTEGER}");
		}

		if (record.getCalibrationmode() != null) {
			sql.VALUES("CalibrationMode", "#{calibrationmode,jdbcType=INTEGER}");
		}

		if (record.getMutiserverflag() != null) {
			sql.VALUES("MutiServerFlag", "#{mutiserverflag,jdbcType=VARCHAR}");
		}

		if (record.getLedLastresponsetime() != null) {
			sql.VALUES("LED_LastResponseTime", "#{ledLastresponsetime,jdbcType=VARCHAR}");
		}

		if (record.getLedLastsendtime() != null) {
			sql.VALUES("LED_LastSendTime", "#{ledLastsendtime,jdbcType=VARCHAR}");
		}

		if (record.getSendbytescount() != null) {
			sql.VALUES("SendBytesCount", "#{sendbytescount,jdbcType=BIGINT}");
		}

		if (record.getReceivebytescount() != null) {
			sql.VALUES("ReceiveBytesCount", "#{receivebytescount,jdbcType=BIGINT}");
		}

		if (record.getWritedate() != null) {
			sql.VALUES("WriteDate", "#{writedate,jdbcType=VARCHAR}");
		}

		if (record.getLedfactoryflag() != null) {
			sql.VALUES("LedFactoryFlag", "#{ledfactoryflag,jdbcType=VARCHAR}");
		}

		if (record.getLedversion() != null) {
			sql.VALUES("LedVersion", "#{ledversion,jdbcType=VARCHAR}");
		}

		if (record.getStateledversion() != null) {
			sql.VALUES("StateLedVersion", "#{stateledversion,jdbcType=VARCHAR}");
		}

		if (record.getLedparametersflag() != null) {
			sql.VALUES("LedParametersFlag", "#{ledparametersflag,jdbcType=BIGINT}");
		}

		if (record.getLedstatestring() != null) {
			sql.VALUES("LedStateString", "#{ledstatestring,jdbcType=VARCHAR}");
		}

		if (record.getRegisterdate() != null) {
			sql.VALUES("RegisterDate", "#{registerdate,jdbcType=VARCHAR}");
		}

		if (record.getLevelevaluate() != null) {
			sql.VALUES("LevelEvaluate", "#{levelevaluate,jdbcType=VARCHAR}");
		}

		if (record.getCarpoolflag() != null) {
			sql.VALUES("CarPoolFlag", "#{carpoolflag,jdbcType=BIGINT}");
		}

		if (record.getInspect() != null) {
			sql.VALUES("Inspect", "#{inspect,jdbcType=INTEGER}");
		}

		if (record.getLedupgrading() != null) {
			sql.VALUES("LedUpgrading", "#{ledupgrading,jdbcType=INTEGER}");
		}

		if (record.getLedupgradeindex() != null) {
			sql.VALUES("LedUpgradeIndex", "#{ledupgradeindex,jdbcType=INTEGER}");
		}

		if (record.getCarnumber() != null) {
			sql.VALUES("CarNumber", "#{carnumber,jdbcType=VARCHAR}");
		}

		if (record.getCarnumberset() != null) {
			sql.VALUES("CarNumberSet", "#{carnumberset,jdbcType=VARCHAR}");
		}

		if (record.getProjectid() != null) {
			sql.VALUES("projectid", "#{projectid,jdbcType=VARCHAR}");
		}

		if (record.getStarlevel() != null) {
			sql.VALUES("StarLevel", "#{starlevel,jdbcType=INTEGER}");
		}

		if (record.getStarlevelset() != null) {
			sql.VALUES("StarLevelSet", "#{starlevelset,jdbcType=INTEGER}");
		}

		if (record.getDisconnect() != null) {
			sql.VALUES("disconnect", "#{disconnect,jdbcType=INTEGER}");
		}

		if (record.getUpdaterate() != null) {
			sql.VALUES("UpdateRate", "#{updaterate,jdbcType=DOUBLE}");
		}

		if (record.getAdidlist() != null) {
			sql.VALUES("AdIdList", "#{adidlist,jdbcType=LONGVARCHAR}");
		}

		if (record.getPlaylist() != null) {
			sql.VALUES("PlayList", "#{playlist,jdbcType=LONGVARCHAR}");
		}

		return sql.toString();
	}

	public String updateByPrimaryKeySelective(terminal record) {
		SQL sql = new SQL();
		sql.UPDATE("t_terminals");

		if (record.getName() != null) {
			sql.SET("Name = #{name,jdbcType=VARCHAR}");
		}

		if (record.getGroupid() != null) {
			sql.SET("GroupID = #{groupid,jdbcType=INTEGER}");
		}

		if (record.getLedId() != null) {
			sql.SET("LED_ID = #{ledId,jdbcType=VARCHAR}");
		}

		if (record.getSimno() != null) {
			sql.SET("SIMNo = #{simno,jdbcType=VARCHAR}");
		}

		if (record.getTaxitype() != null) {
			sql.SET("TaxiType = #{taxitype,jdbcType=VARCHAR}");
		}

		if (record.getComanyname() != null) {
			sql.SET("ComanyName = #{comanyname,jdbcType=VARCHAR}");
		}

		if (record.getDriver() != null) {
			sql.SET("driver = #{driver,jdbcType=VARCHAR}");
		}

		if (record.getTel() != null) {
			sql.SET("tel = #{tel,jdbcType=VARCHAR}");
		}

		if (record.getPapertype() != null) {
			sql.SET("paperType = #{papertype,jdbcType=VARCHAR}");
		}

		if (record.getPaperno() != null) {
			sql.SET("paperNo = #{paperno,jdbcType=VARCHAR}");
		}

		if (record.getRemark() != null) {
			sql.SET("remark = #{remark,jdbcType=VARCHAR}");
		}

		if (record.getDelindex() != null) {
			sql.SET("DelIndex = #{delindex,jdbcType=INTEGER}");
		}

		if (record.getCalibrationmode() != null) {
			sql.SET("CalibrationMode = #{calibrationmode,jdbcType=INTEGER}");
		}

		if (record.getMutiserverflag() != null) {
			sql.SET("MutiServerFlag = #{mutiserverflag,jdbcType=VARCHAR}");
		}

		if (record.getLedLastresponsetime() != null) {
			sql.SET("LED_LastResponseTime = #{ledLastresponsetime,jdbcType=VARCHAR}");
		}

		if (record.getLedLastsendtime() != null) {
			sql.SET("LED_LastSendTime = #{ledLastsendtime,jdbcType=VARCHAR}");
		}

		if (record.getSendbytescount() != null) {
			sql.SET("SendBytesCount = #{sendbytescount,jdbcType=BIGINT}");
		}

		if (record.getReceivebytescount() != null) {
			sql.SET("ReceiveBytesCount = #{receivebytescount,jdbcType=BIGINT}");
		}

		if (record.getWritedate() != null) {
			sql.SET("WriteDate = #{writedate,jdbcType=VARCHAR}");
		}

		if (record.getLedfactoryflag() != null) {
			sql.SET("LedFactoryFlag = #{ledfactoryflag,jdbcType=VARCHAR}");
		}

		if (record.getLedversion() != null) {
			sql.SET("LedVersion = #{ledversion,jdbcType=VARCHAR}");
		}

		if (record.getStateledversion() != null) {
			sql.SET("StateLedVersion = #{stateledversion,jdbcType=VARCHAR}");
		}

		if (record.getLedparametersflag() != null) {
			sql.SET("LedParametersFlag = #{ledparametersflag,jdbcType=BIGINT}");
		}

		if (record.getLedstatestring() != null) {
			sql.SET("LedStateString = #{ledstatestring,jdbcType=VARCHAR}");
		}

		if (record.getRegisterdate() != null) {
			sql.SET("RegisterDate = #{registerdate,jdbcType=VARCHAR}");
		}

		if (record.getLevelevaluate() != null) {
			sql.SET("LevelEvaluate = #{levelevaluate,jdbcType=VARCHAR}");
		}

		if (record.getCarpoolflag() != null) {
			sql.SET("CarPoolFlag = #{carpoolflag,jdbcType=BIGINT}");
		}

		if (record.getInspect() != null) {
			sql.SET("Inspect = #{inspect,jdbcType=INTEGER}");
		}

		if (record.getLedupgrading() != null) {
			sql.SET("LedUpgrading = #{ledupgrading,jdbcType=INTEGER}");
		}

		if (record.getLedupgradeindex() != null) {
			sql.SET("LedUpgradeIndex = #{ledupgradeindex,jdbcType=INTEGER}");
		}

		if (record.getCarnumber() != null) {
			sql.SET("CarNumber = #{carnumber,jdbcType=VARCHAR}");
		}

		if (record.getCarnumberset() != null) {
			sql.SET("CarNumberSet = #{carnumberset,jdbcType=VARCHAR}");
		}

		if (record.getProjectid() != null) {
			sql.SET("projectid = #{projectid,jdbcType=VARCHAR}");
		}

		if (record.getStarlevel() != null) {
			sql.SET("StarLevel = #{starlevel,jdbcType=INTEGER}");
		}

		if (record.getStarlevelset() != null) {
			sql.SET("StarLevelSet = #{starlevelset,jdbcType=INTEGER}");
		}

		if (record.getDisconnect() != null) {
			sql.SET("disconnect = #{disconnect,jdbcType=INTEGER}");
		}

		if (record.getUpdaterate() != null) {
			sql.SET("UpdateRate = #{updaterate,jdbcType=DOUBLE}");
		}

		if (record.getAdidlist() != null) {
			sql.SET("AdIdList = #{adidlist,jdbcType=LONGVARCHAR}");
		}

		if (record.getPlaylist() != null) {
			sql.SET("PlayList = #{playlist,jdbcType=LONGVARCHAR}");
		}

		sql.WHERE("DtuKey = #{dtukey,jdbcType=VARCHAR}");

		return sql.toString();
	}
}