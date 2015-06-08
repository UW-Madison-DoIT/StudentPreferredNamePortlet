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

<portlet:actionURL portletMode="VIEW" var="savePreferredNameURL">
  <portlet:param name="action" value="savePreferredName" />
</portlet:actionURL>
<portlet:actionURL portletMode="VIEW" var="deletePreferredNameURL">
  <portlet:param name="action" value="delete" />
</portlet:actionURL>

<portlet:renderURL var="cancelAction" portletMode="VIEW" windowState="NORMAL" />

<spring:message code="savePreferredName" var="savePreferredName" text="Save"/>

<div id="${n}student-preferred-name" class="student-preferred-name">
      <div class="contact-info-official-name">
          <span class="uportal-channel-strong"><spring:message code="label.official.name"/>:</span>
          <span>${legalName}</span>
        </div>
      <div class="contact-info-pref-name-view ${n}view">
              <span class="uportal-channel-strong"><spring:message code="label.preferred.name"/>:</span>
              <span>${firstName}
                      <c:if test="${!empty middleName }">
                          &nbsp;${middleName}
                      </c:if>
                      <c:if test="${!empty firstName }">
                        <c:choose>
                            <c:when test="${!empty lastName}">
                                &nbsp;${lastName}
                            </c:when>
                            <c:otherwise>
                                &nbsp;${sirName}
                            </c:otherwise>
                        </c:choose>
                      </c:if>
                      &nbsp;<span class="uportal-channel-table-caption">${pendingStatus }</span>
                      &nbsp;<a href="#" onclick="studentPreferredNamePortlet.displayEdit(true);"><spring:message code="edit"/></a>
                      &nbsp;<a href="${deletePreferredNameURL}" onclick="return confirm('Are you sure you want to delete your preferred name?');"><spring:message code="delete"/></a>
              </span>
          </div>
          <div class='edit-area'>
          <form action="${savePreferredNameURL}" method="post">
            <spring:nestedPath path="preferredName">
                  <div class="contact-info-pref-name-edit ${n}edit" style="display: none;">
                      <span class="uportal-channel-strong">
                          <spring:message code="label.preferred.name"/>:
                      </span>
                      <div class='${n}edit-error pref-name-edit-error' style="display: none; padding: .5em;">
                          <span><form:errors path="firstName" cssClass="error"/>
                              &nbsp;<form:errors path="middleName" cssClass="error"/>
                              &nbsp;<form:errors path="lastName" cssClass="error"/>
                        </span>
                      </div>
                      <div class='pref-name-edit'>
                          <div class='names'>
                              <div class="edit-name">
                              <span class='label'>First</span>
                              <br/>
                              <span>
                                  <form:input path="firstName" class="uportal-input-text ${n}first-name" maxlength="30" />
                              </span>
                              </div>
                              <div class="edit-name">
                              <span class='label'>Middle</span>
                              <br/>
                              <span>
                                  <form:input path="middleName" class="uportal-input-text ${n}middle-name" maxlength="30" />
                              </span>
                              </div>
                            <div class="edit-name">
                            <span class='label'>Last*</span>
                            <br/>
                            <span>
                                <form:input path="lastName" class="uportal-input-text ${n}last-name" maxlength="30" />
                            </span>
                            </div>
                          </div>
                          <div class='info-text'>
                            *<spring:message code='error.lastNameWeirdLogicError'/>
                          </div>
                          <div class="edit-buttons">
                              <span>
                                  <input class="uportal-button fancy-button btn btn-primary" value="${savePreferredName}" type="submit">
                              </span>
                              <span>
                                  <a href="#" onclick='studentPreferredNamePortlet.displayEdit(false);' class="uportal-button fancy-cancel btn btn-default"><spring:message code="button.cancel" text="Cancel"/></a>
                              </span>
                              
                          </div>
                          
                      </div>
                  </div>
              </spring:nestedPath>
        </form>
        </div>
        <div class='edit-notice'>
              <c:if test="${!empty prefs['notice'][0]}">
              <p>
                   ${prefs['notice'][0]}
              </p>
            </c:if>
        </div>
</div>

<script type="text/javascript">
var fname = "";
var mname = "";
(function($) {
   $(document).ready(function() {
      $(".${n}edit").hide();
      $(".${n}edit-error").hide();
      fname = $(".${n}first-name").val();
      mname = $(".${n}middle-name").val();
      lname = $(".${n}last-name").val();
      
      studentPreferredNamePortlet.displayEdit = function (enable) {
          if(enable) {
              $(".${n}edit").show();
              $(".${n}view").hide();
              
          } else {
              $(".${n}edit").hide();
              $(".${n}edit-error").hide();
              $(".${n}view").show();
              $(".${n}first-name").val(fname);
              $(".${n}middle-name").val(mname);
              $(".${n}last-name").val(lname);
              fname = "";
              mname = "";
              lname = "";
          }
      }
   });            
})(studentPreferredNamePortlet.jQuery);
</script>

<c:if test="${!empty therewasanerror }">
<script type="text/javascript">
(function($) {
   $(document).ready(function() {
       studentPreferredNamePortlet.displayEdit(true);
       $(".${n}edit-error").show().delay();
   });            
})(studentPreferredNamePortlet.jQuery);    
</script>
</c:if>