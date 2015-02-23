<%--
  Created by IntelliJ IDEA.
  User: Vlad
  Date: 07.02.2015
  Time: 16:49
  To change this template use File | Settings | File Templates.
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>testShop</title>
</head>
<body>
<h1 style="text-align: center">List of product</h1>
<a href="/" style="color: #000;text-decoration: none">main</a>
<ul>
    <c:forEach var="product" items="${productList}">

        <li style="padding-bottom: 10px;">
            <a href="productPage.do?id=${product.id}"
               style="color: #000;text-decoration: none">${product.name}</a>
            <a href="AddToBucket.do?id=${product.id}"
               style="color: #000;text-decoration: none">(+)</a>
            <a href="RemoveFromBucket.do?id=${product.id}"
               style="color: #000;text-decoration: none">(-)</a>
        </li>

    </c:forEach>
</ul>
<h2>Product in bucket</h2>
<ul>
    <c:forEach var="productInBucket" items="${productsInBucket}">
        <li>
            <a href="productPage.do?id=${productInBucket.key.id}"
               style="color: #000;text-decoration: none">${productInBucket.key.name}</a> =${productInBucket.value}
        </li>
    </c:forEach>
</ul>

<div style="position: fixed;right: 30px;bottom: 30px;">You visit this page ${visitCount} times</div>
</body>
</html>
