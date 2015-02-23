<%--
  Created by IntelliJ IDEA.
  User: Vlad
  Date: 07.02.2015
  Time: 16:45
  To change this template use File | Settings | File Templates.
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>testShop</title>
</head>
<body>
<h1 style="text-align: center">Product</h1>

<h1>name: ${product.getName()}</h1>

<h2>ID: ${product.getId()}</h2>

<div><a href="allProductsPage.do" style="color: #000;text-decoration: none">all product</a></div>
<div><a href="/" style="color: #000;text-decoration: none">main</a></div>
<div><a href="AddToBucket.do?id=${product.id}" style="color: #000;text-decoration: none;font-weight: bold;">add to
    bucket</a></div>

<h2>Product in bucket</h2>
<ul>
    <c:forEach var="productInBucket" items="${productsInBucket}">
        <li>
            <a href="productPage.do?id=${productInBucket.key.id}"
               style="color: #000;text-decoration: none">${productInBucket.key.name}</a> =${productInBucket.value}
            <a href="AddToBucket.do?id=${productInBucket.key.id}"
               style="color: #000;text-decoration: none">(+)</a>
            <a href="RemoveFromBucket.do?id=${productInBucket.key.id}"
               style="color: #000;text-decoration: none">(-)</a>
        </li>
    </c:forEach>
</ul>
</body>
</html>
