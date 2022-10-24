<%--
  Created by IntelliJ IDEA.
  User: yosaf
  Date: 5/22/2019
  Time: 9:25 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp"></jsp:include>
<div class="main">
    <div class="container">
        <div class="section" style="padding-top: 100px">
            <div class="row">
                <div class="col-md-8 ml-auto mr-auto">
                    <form autocomplete="off" class="form" id="search" method="POST" action="/search">
                        <div id="inputSearch" class="input-group form-group-no-border input-lg">
                            <input type="text" name="query" required="true" class="form-control" value="${query}" placeholder="Search">
                            <span class="input-group-addon" onclick="submitForm();">
                                <i class="now-ui-icons ui-1_zoom-bold"></i>
                            </span>
                        </div>
                        <div class="row">
                            <div class="col-md-5 ml-auto mr-auto">
                                <div class="form-check form-check-radio">
                                    <label class="form-check-label">
                                        <input class="form-check-input" type="radio" name="fcm" value="0" checked>
                                        <span class="form-check-sign"></span>
                                        <strong>Non FCM Cluster Based</strong>
                                    </label>
                                </div>
                            </div>
                            <div class="col-md-5 ml-auto mr-auto">
                                <div class="form-check form-check-radio">
                                    <label class="form-check-label">
                                        <input class="form-check-input" type="radio" name="fcm" value="1">
                                        <span class="form-check-sign"></span>
                                        <strong>FCM Cluster Based</strong>
                                    </label>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="col-md-7 ml-auto mr-auto">
                    <br>
                    <c:choose>
                        <c:when test="${results.results.size() != 0}">
                            Menampilkan hasil untuk <strong>"${query}"</strong> ( ${results.timeExecute} milliseconds ) menggunakan pencarian ${type}
                        </c:when>
                        <c:when test="${results.results.size() == 0}">
                            Tidak ditemukan hasil untuk <strong>"${query}"</strong> ( ${results.timeExecute} milliseconds ) menggunakan pencarian ${type}
                        </c:when>
                    </c:choose>

                </div>
            </div>
            <div class="row">
                <div class="col-md-10 ml-auto mr-auto">
                    <hr>
                </div>
                <div class="col-md-10 ml-auto mr-auto">
                    <c:forEach items="${results.results}" var="doc">
                        <div class="card card-plain card-blog">
                            <div class="row">
                                <div class="col-md-12">
                                    <h5 class="card-title">
                                        <a href="/doc/${doc.document.id}">${doc.document.judul}</a>
                                    </h5>
<%--                                    <p class="card-description">--%>
<%--                                            ${doc.document.content} <a href="/doc/${doc.document.id}"> Read More </a>--%>
<%--                                    </p>--%>
                                    <p class="card-description" style="color: black">
                                            ${doc.document.content} <strong><a href="/doc/${doc.document.id}"> Read More </a></strong>
                                    </p>
                                    <div class="author">
                                        Kategori :
                                        <span>${doc.document.kategori}</span>
                                    </div>
                                    <div class="author">
                                        Score :
                                        <span>${doc.score}</span>
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