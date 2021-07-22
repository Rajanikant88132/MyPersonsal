package com.example.uploadingfiles;

public class FileDetail {

String CustFileName;
String formatedCustFileName;
String  MsgFileName;
String formatedMsgFileName;
String  application;
String  description;
String  incident;
String  submitter;
String status;
String  submitedDate;
String LatestUpdateDate;
String  CompletionDate;

public String getCustFileName() {
	return CustFileName;
}
public void setCustFileName(String custFileName) {
	CustFileName = custFileName;
}
public String getFormatedCustFileName() {
	return formatedCustFileName;
}
public void setFormatedCustFileName(String formatedCustFileName) {
	this.formatedCustFileName = formatedCustFileName;
}
public String getMsgFileName() {
	return MsgFileName;
}
public void setMsgFileName(String msgFileName) {
	MsgFileName = msgFileName;
}
public String getFormatedMsgFileName() {
	return formatedMsgFileName;
}
public void setFormatedMsgFileName(String formatedMsgFileName) {
	this.formatedMsgFileName = formatedMsgFileName;
}
public String getApplication() {
	return application;
}
public void setApplication(String application) {
	this.application = application;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public String getIncident() {
	return incident;
}
public void setIncident(String incident) {
	this.incident = incident;
}
public String getSubmitter() {
	return submitter;
}
public void setSubmitter(String submitter) {
	this.submitter = submitter;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getSubmitedDate() {
	return submitedDate;
}
public void setSubmitedDate(String submitedDate) {
	this.submitedDate = submitedDate;
}
public String getLatestUpdateDate() {
	return LatestUpdateDate;
}
public void setLatestUpdateDate(String latestUpdateDate) {
	LatestUpdateDate = latestUpdateDate;
}
public String getCompletionDate() {
	return CompletionDate;
}
public void setCompletionDate(String completionDate) {
	CompletionDate = completionDate;
}

}
