<%--
  Created by IntelliJ IDEA.
  User: scott1991
  Date: 01.02.2022
  Time: 13:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
  <title>All Reports</title>
  <link rel="stylesheet"
        href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
        integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
        crossorigin="anonymous">

  <script src="http://code.jquery.com/jquery-2.0.3.min.js" data-semver="2.0.3" data-require="jquery"></script>
  <link href="//cdnjs.cloudflare.com/ajax/libs/datatables/1.9.4/css/jquery.dataTables_themeroller.css" rel="stylesheet" data-semver="1.9.4" data-require="sortable@*" />
  <link href="//cdnjs.cloudflare.com/ajax/libs/datatables/1.9.4/css/jquery.dataTables.css" rel="stylesheet" data-semver="1.9.4" data-require="sortable@*" />
  <link href="//cdnjs.cloudflare.com/ajax/libs/datatables/1.9.4/css/demo_table_jui.css" rel="stylesheet" data-semver="1.9.4" data-require="sortable@*" />
  <link href="//cdnjs.cloudflare.com/ajax/libs/datatables/1.9.4/css/demo_table.css" rel="stylesheet" data-semver="1.9.4" data-require="sortable@*" />
  <link href="//cdnjs.cloudflare.com/ajax/libs/datatables/1.9.4/css/demo_page.css" rel="stylesheet" data-semver="1.9.4" data-require="sortable@*" />
  <link data-require="jqueryui@*" data-semver="1.10.0" rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/jqueryui/1.10.0/css/smoothness/jquery-ui-1.10.0.custom.min.css" />
  <script data-require="jqueryui@*" data-semver="1.10.0" src="//cdnjs.cloudflare.com/ajax/libs/jqueryui/1.10.0/jquery-ui.js"></script>
  <script src="//cdnjs.cloudflare.com/ajax/libs/datatables/1.9.4/jquery.dataTables.js" data-semver="1.9.4" data-require="sortable@*"></script>
  <link rel="stylesheet" type="text/css" href="css/check.css">
  <script src="js/script.js"></script>
  <link rel="stylesheet" type="text/css" href="css/table.css" />
  <link rel="stylesheet" type="text/css" href="css/style.css" />
  <script src="js/moment.min.js"></script>
</head>
<body>


<header>
  <nav class="navbar navbar-expand-md navbar-dark"
       style="background-color: blue">
    <div>
      <a href="/" class="navbar-brand"> Tax Report </a>
    </div>
    <c:if test='${!user.getRole().equals("insp")}'>
    <ul class="navbar-nav">
      <li><a href="<%=request.getContextPath()%>/UploadReport"
             class="nav-link">Create Reports</a></li>
    </ul>
    </c:if>
    <c:if test='${user.getRole().equals("insp")}'>
      <ul class="navbar-nav">
        <li><a href="<%=request.getContextPath()%>/changePassword.jsp"
               class="nav-link">Edit password</a></li>
      </ul>
    </c:if>
    <ul class="navbar-nav">
      <li><a href="<%=request.getContextPath()%>/logOff"
             class="nav-link">logOff</a></li>
    </ul>
  </nav>
</header>
<br>

<div class="row">
<script>
    $(document).ready(function () {
        $("input:checkbox").on("change", function () {
            var a = $("input:checkbox:checked").map(function () {
                return $(this).val()
            }).get()
            $("#sortable tr").show();
            var status = $(".status").filter(function () {
                var stat = $(this).text(),
                    index = $.inArray(stat, a);
                return index < 0
            }).parent().hide();
        })
    });

</script>

  <div class="container">
    <h3 class="text-center">List of Reports</h3>
    <hr>

    <div class="container text-left">
      <c:if test='${!user.getRole().equals("insp")}'>
        <a href="/UploadReport" class="btn btn-success">Add
          Report</a>
      </c:if>
      <c:if test='${user.getRole().equals("indi")}'>
        <a href="/accountInd" class="btn btn-success">Account</a>
      </c:if>
      <c:if test='${user.getRole().equals("entyti")}'>
        <a href="/accountEntyti" class="btn btn-success">Account</a>
      </c:if>
      <a href="/reportList" class="btn btn-success">Show Reports</a>
      <c:if test='${user.getRole().equals("insp")}'>
        <a href="/reportList?archive=1" class="btn btn-success">Archive</a>
      </c:if>
    </div>

    <br>
    <p id="date_filter">
      <span id="date-label-from" class="date-label">Date from: </span><input placeholder="dd.MM.yyyy" class="date_range_filter date" type="text" id="datepicker_from" />
      <span id="date-label-to" class="date-label">To:<input placeholder="dd.MM.yyyy" class="date_range_filter date" type="text" id="datepicker_to" /></span>
    </p>

    <div class="checkbox" id="filter">
      <ul class="ks-cboxtags">
        <li><input type="checkbox" id="checkboxOne" value="1"><label for="checkboxOne">Filed</label></li>
        <li><input type="checkbox" id="checkboxTwo" value="2" checked><label for="checkboxTwo">Accepted</label></li>
        <li><input type="checkbox" id="checkboxThree" value="3" checked><label for="checkboxThree">Reject</label></li>
        <c:if test='${!user.getRole().equals("insp")}'>
          <li><input type="checkbox" id="checkboxFour" value="4"><label for="checkboxFour">Edit</label></li>
        </c:if>
        <li><input type="checkbox" id="checkboxFive" value="5"><label for="checkboxFive">Update</label></li>
        <li><input type="checkbox" id="checkboxSix" value="6" checked><label for="checkboxSix">Processing</label></li>
      </ul>
    </div>
    <br>
    <table class="table table-bordered" id="sortable" name ="sortable" >
      <thead>
      <tr>
        <th data-type="number">ID</th>
        <th>Status</th>
        <th>Date</th>
        <c:if test='${user.getRole().equals("insp")}'>
          <th>Creater</th>
        </c:if>
        <th>Description</th>

          <th>Inspector</th>

        <th>Comments</th>
        <th>File</th>
        <c:if test='${user.getRole().equals("insp")}'>
        <c:if test='${!arc}'>
          <th  width="13%">TODO</th>
        </c:if>
        </c:if>
      </tr>
      </thead>
      <tbody class="table1">


      <c:forEach var="repo" items="${list}" >
        <tr>
          <td>${repo.id}</td>
          <td class="status">${repo.status}</td>
          <td><fmt:formatDate type="date" value="${repo.date}" pattern="dd.MM.yyyy"/></td>

          <c:if test='${user.getRole().equals("insp")}'>
          <td><a href="/userInf?id=${repo.creater}"> ${repo.createrName}</a></td>
          </c:if>

          <td>${repo.description}</td>

          <td>${repo.inspector}</td>


          <td>${repo.comments}</td>

          <td><a href="/${repo.filePath}">Open</a>
            <a href="/download?filePath=${repo.filePath}"> Download</a>
            <c:if test='${!user.getRole().equals("insp")}'>
            <c:if test="${repo.status == 1}"><br>
              <form action="/deleteRepo" method="post">
                <input type="hidden" name="id" value="${repo.id}">
                <input type="hidden" name="file" value="${repo.filePath}">
                <input type="submit" value="Delete">
              </form>
            </c:if>
            <c:if test="${repo.status == 4}"><br>
              <a href="/UploadReport?id=${repo.id}"> Edit</a>
            </c:if>
            </c:if>
              </td>
            <c:if test='${user.getRole().equals("insp")}'>
          <c:if test='${!arc}'>
          <td>
            <form name="form" action="/inspections" method="post" >
              <input type="hidden" name="id" value="${repo.id}">
              <input type="hidden" name="userId" value="${user.getId()}">
            <select class="select-css" id="works" name="works">
              <option value="2">Accepted</option>
              <option value="3">Reject</option>
              <option value="4">Edit</option>
              <option value="6">Processing</option>
            </select>

              <input type="submit" value="Edit"></input>
            </form>
          </td>

          </c:if>
          </c:if>

      </c:forEach>

      </tbody>

    </table>
  </div>
</div>
</body>
</html>

