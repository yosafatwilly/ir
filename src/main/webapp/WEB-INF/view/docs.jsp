<%--
  Created by IntelliJ IDEA.
  User: yosaf
  Date: 5/22/2019
  Time: 9:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp"></jsp:include>
<div class="main">
    <div class="section" style="padding-top: 100px">
        <div class="container">
            <div class="col text-center">
                <h2 class="section-title">Documents</h2>
            </div>
            <hr>
            <div class="row">
                <div class="col-md-3" style="padding-top: 13px">
                    <p class="category text-center">Category</p>
                    <ul class="nav nav-pills nav-pills-primary flex-column" role="tablist">
                        <c:choose>
                            <c:when test="${active != null}">
                                <li class="nav-item">
                                    <a class="nav-link" href="/docs">
                                        Show All
                                    </a>
                                </li>
                            </c:when>
                            <c:when test="${active == null}">
                                <li class="nav-item">
                                    <a class="nav-link active" href="/docs">
                                        Show All
                                    </a>
                                </li>
                            </c:when>
                        </c:choose>
                        <c:forEach items="${cat}" var="cat">
                            <c:choose>
                                <c:when test="${active == cat}">
                                    <li class="nav-item">
                                        <a class="nav-link active" href="/docs/${cat}">
                                                ${cat}
                                        </a>
                                    </li>
                                </c:when>
                                <c:when test="${active != cat}">
                                    <li class="nav-item">
                                        <a class="nav-link" href="/docs/${cat}">
                                                ${cat}
                                        </a>
                                    </li>
                                </c:when>
                            </c:choose>
                        </c:forEach>
                    </ul>
                </div>
                <div class="col-md-9">
                    <c:forEach items="${list}" var="doc">
                        <div class="card card-plain card-blog">
                            <div class="row">
                                <div class="col-md-12">
                                    <h5 class="card-title">
                                        <a href="/doc/${doc.id}">${doc.judul}</a>
                                    </h5>
                                    <p class="card-description" style="color: black">
                                            ${doc.content} <strong><a href="/doc/${doc.id}"> Read More </a></strong>
                                    </p>
                                    <div class="author">
                                        Kategori :
                                        <span>${doc.kategori}</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <hr>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="footer.jsp"></jsp:include>
