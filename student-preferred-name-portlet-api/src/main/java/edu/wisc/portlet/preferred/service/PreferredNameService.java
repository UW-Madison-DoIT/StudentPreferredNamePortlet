package edu.wisc.portlet.preferred.service;

import edu.wisc.portlet.preferred.form.PreferredName;
import edu.wisc.portlet.preferred.form.PreferredNameExtended;

public interface PreferredNameService {
	public PreferredName getPreferredName(String pvi);
	
	public PreferredNameExtended getPreferredNameAndLegalName(String pvi);
	
	public String getStatus(PreferredName pn);
	
	public void setPreferredName(PreferredName pn);
	
	public void updateHideSource(PreferredName pn);
	
	public void deletePreferredName(String pvi);

	public void deletePreferredNameAdmin(String pvi);
	
	public String getPviFromNetId(String netId);
}
