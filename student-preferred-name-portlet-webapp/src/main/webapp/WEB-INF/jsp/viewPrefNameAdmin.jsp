<%--

    Copyright 2012, Board of Regents of the University of
    Wisconsin System. See the NOTICE file distributed with
    this work for additional information regarding copyright
    ownership. Board of Regents of the University of Wisconsin
    System licenses this file to you under the Apache License,
    Version 2.0 (the "License"); you may not use this file
    except in compliance with the License. You may obtain a
    copy of the License at:

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on
    an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied. See the License for the
    specific language governing permissions and limitations
    under the License.

--%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ include file="/WEB-INF/jsp/header.jsp"%>

<portlet:actionURL portletMode="VIEW" var="editPreferredNameURL">
  <portlet:param name="action" value="savePreferredName" />
</portlet:actionURL>

<portlet:actionURL portletMode="VIEW" var="deletePrefNameAdminURL">
	<portlet:param name="action" value="deleteAdmin"/>
	<portlet:param name="pvi" value="${preferredName.pvi }"/>
</portlet:actionURL>

<portlet:renderURL var="cancelAction" portletMode="VIEW" windowState="NORMAL" />

<spring:message code="save" var="save" text="Save"/>

<div id="${n}student-preferred-name" class="student-preferred-name">

	<div class='modify-preferred-name'>
		<form action="${editPreferredNameURL}" method="post">
	  		<spring:nestedPath path="preferredName">
				<c:if test="${!empty error || !empty hasError}">
					<div class="error">
						<span>${error}</span>
						<span><form:errors path="firstName" cssClass="error"/>&nbsp;<form:errors path="middleName" cssClass="error"/>
                              &nbsp;<form:errors path="lastName" cssClass="error"/></span>
					</div>
				</c:if>
	  			<form:hidden path="pvi"/>
				<div class="student-preferred-name ${n}-admin-edit-pref-name">
			  		<div>
				  		<div class="source">
				  			<span class="uportal-channel-strong">${sourceLabel}: </span><span>${source}</span>
				  		</div>
				  		<div class="source">
				  			<span class="uportal-channel-strong">Primary/Legal Name: </span><span>${preferredName.legalName}</span>
				  		</div>
				  		<div>
			  				<span class="uportal-channel-strong">Name in Use: </span>
			  			</div>
				  		<div style="margin-left: 1em;">
				  			<div class="edit-name">
						  		<span>
						  			${preferredName.firstName }
						  			<form:hidden path="firstName" class="${n}first-name" />
						  		</span>
						  		<br/>
						  		<span class='label'>First Name</span>
						  	</div>
						  	<div class="edit-name">
						  		<span>
						  			${preferredName.middleName }
						  			<form:hidden path="middleName" class="${n}middle-name" />
						  		</span>
						  		<br/>
						  		<span class='label'>Middle Name</span>
					  		</div>
                            <div class="edit-name">
                                <span>
                                    ${preferredName.lastName }
                                    <form:hidden path="lastName" class="${n}last-name" />
                                </span>
                                <br/>
                                <span class='label'>Last Name</span>
                            </div>
					  		<div class="edit-name" style="padding-top:3px; margin-left:0em; padding-right:0em;">
						  		<span>
						  			<form:checkbox path="hideSource" class="uportal-input-text ${n}hide-legal-name" />
						  		</span>
					  		</div>
					  		<span class='label'>Hide Primary/Legal Name</span>
				  		</div>
				  		<div class="edit-buttons">
				  			<span>
					  			<input class="uportal-button fancy-button" value="${save}" type="submit">
					  		</span>
					  		<span>
					  			<a href="${deletePrefNameAdminURL}" class="uportal-button" onclick="return confirm('Are you sure you want to delete this Name in Use and mark it as inappropriate?');">Delete</a>
					  		</span>
					  		<span>
					  			<a href="${ cancelAction }" class="uportal-button">Cancel</a>
					  		</span>
				  		</div>

			  		</div>
			  	</div>
		  	</spring:nestedPath>
		</form>
	</div>
</div>

<script type="text/javascript">
(function($) {
   $(document).ready(function() {

   });
})(studentPreferredNamePortlet.jQuery);
</script>
