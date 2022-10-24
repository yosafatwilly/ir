<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp"></jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<div class="container">
    <div class="section" style="padding-top: 100px">
        <div class="container">
            <div class="col text-center">
                <h3 class="section-title">Query & Relevant Documents</h3>
            </div>
            <hr>
            <c:forEach items="${query}" var="cat">
            <div class="cd-section">
                <h4 class="title">Query "${fn:toUpperCase(cat.query)}"</h4>
                <p>Jumlah Dokumen Relevan = ${cat.documents.size()}</p>
                <div id="tables">
                    <div class="row">
                        <div class="col-md-12 ml-auto mr-auto text-center">
                            <div class="card card-plain">
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table class="table table-striped">
                                            <thead class="">
                                            <th>
                                                No
                                            </th>
                                            <th>
                                                Dokumen
                                            </th>
                                            </thead>
                                            <tbody>
                                            <% int i = 1; %>
                                            <c:forEach items="${cat.documents}" var="rp">
                                                <tr>
                                                    <td>
                                                        <%=i%>
                                                    </td>
                                                    <td>
                                                        <a href="/doc/${rp.id}">${rp.judul}</a>
                                                    </td>
                                                </tr>
                                                <% i++; %>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
                <hr>
            </c:forEach>
        </div>
    </div>
</div>
<jsp:include page="footer.jsp"></jsp:include>