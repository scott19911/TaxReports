
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE >
<html>
<head>
    <title>Comments</title>
</head>
<body>

<div style="padding:5px; color:red;font-style:italic;">
    ${errorMessage}
</div>

<h2>Comment report</h2>
<script>
    function validate()
    {

        var comment = document.form.login.comm;

        if (comment==null || comment.length == 0)
        {
            alert("Comment can't be blank");
            return false;
        }

        else if (comment.length >300)
        {
            alert("Comment can't be great then 300");
            return false;
        }
    }
</script>
<form method="post" action="${pageContext.request.contextPath}/comments" onsubmit="return validate()">

        Comment:
        <br/>
        <input type="text" name="comm" />
        <br />

    <input type="submit" value="Upload" />


</form>

</body>
</html>