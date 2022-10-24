<%--
  Created by IntelliJ IDEA.
  User: yosaf
  Date: 5/22/2019
  Time: 9:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="header.jsp"></jsp:include>
<div class="main">
    <div class="section" style="padding-top: 100px">
        <div class="container">
            <div class="col text-center">
                <h2 class="section-title">Fuzzy C-Means Clustering</h2>
            </div>
            <hr>
            <div class="row">
                <div class="col-md-5 ml-auto mr-auto">
                    <div class="card card-plain" >
                        <div class="card-header" role="tab" id="headingTwo">
                            <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo"
                               aria-expanded="false" aria-controls="collapseTwo">
                                <p id="svg" class="text-center">
                                    <svg width="35px" height="35px" focusable="false" viewBox="0 0 24 24">
                                        <path d="M6,8c1.1,0 2,-0.9 2,-2s-0.9,-2 -2,-2 -2,0.9 -2,2 0.9,2 2,2zM12,20c1.1,0 2,-0.9 2,-2s-0.9,-2 -2,-2 -2,0.9 -2,2 0.9,2 2,2zM6,20c1.1,0 2,-0.9 2,-2s-0.9,-2 -2,-2 -2,0.9 -2,2 0.9,2 2,2zM6,14c1.1,0 2,-0.9 2,-2s-0.9,-2 -2,-2 -2,0.9 -2,2 0.9,2 2,2zM12,14c1.1,0 2,-0.9 2,-2s-0.9,-2 -2,-2 -2,0.9 -2,2 0.9,2 2,2zM16,6c0,1.1 0.9,2 2,2s2,-0.9 2,-2 -0.9,-2 -2,-2 -2,0.9 -2,2zM12,8c1.1,0 2,-0.9 2,-2s-0.9,-2 -2,-2 -2,0.9 -2,2 0.9,2 2,2zM18,14c1.1,0 2,-0.9 2,-2s-0.9,-2 -2,-2 -2,0.9 -2,2 0.9,2 2,2zM18,20c1.1,0 2,-0.9 2,-2s-0.9,-2 -2,-2 -2,0.9 -2,2 0.9,2 2,2z"></path>
                                    </svg>
                                </p>
                            </a>
                        </div>
                        <c:choose>
                        <c:when test="${result.clusterResult.size() == 0}">
<%--                        <c:when test="${true}">--%>
                        <div id="collapseTwo" class="collapse show" role="tabpanel" aria-labelledby="headingTwo">
                            </c:when>
                            <c:otherwise>
                            <div id="collapseTwo" class="collapse" role="tabpanel" aria-labelledby="headingTwo">
                                </c:otherwise>
                                </c:choose>
                                <div class="card-body">
<%--                                    <div class="card card-refine card-plain">--%>
                                        <form method="POST" id="processForm" action="/cluster">
                                            <div class="card-body">
                                                <div class="row">
                                                    <div class="col-md-6 ml-auto mr-auto">
                                                        <label class="col text-center">Jumlah Cluster</label>
                                                        <div class="input-group">
                                                        <span class="input-group-addon">
                                                            <i class="now-ui-icons ui-1_check"></i>
                                                        </span>
                                                            <input type="number" name="jumlahCluster" min="2.0" step="1" required="" value="${current.jumlahCluster}"
                                                                   class="form-control" placeholder="Jumlah Cluster...">
                                                        </div>
                                                    </div>
                                                    <div class="col-md-6 ml-auto mr-auto">
                                                        <label class="col text-center">Max Iterasi</label>
                                                        <div class="input-group">
                                                        <span class="input-group-addon">
                                                            <i class="now-ui-icons ui-1_check"></i>
                                                        </span>
                                                            <input type="number" step="5" name="maxIter" required="" value="${current.maxIter}"
                                                                   class="form-control" placeholder="Max Iterasi...">
                                                        </div>
                                                    </div>
                                                    <div class="col-md-6 ml-auto mr-auto">
                                                        <label class="col text-center">Pangkat</label>
                                                        <div class="input-group">
                                                        <span class="input-group-addon">
                                                            <i class="now-ui-icons ui-1_check"></i>
                                                        </span>
                                                            <input type="number" min="1.0" step="0.01" name="pangkat" required="" value="${current.pangkat}"
                                                                   class="form-control" placeholder="Pangkat...">
                                                        </div>
                                                    </div>
                                                    <div class="col-md-6 ml-auto mr-auto">
                                                        <label class="col text-center">Epsilon</label>
                                                        <div class="input-group">
                                                        <span class="input-group-addon">
                                                            <i class="now-ui-icons ui-1_check"></i>
                                                        </span>
                                                            <input type="text" name="epsilon" required=""
                                                                   value="${current.epsilon}"
                                                                   class="form-control" placeholder="Epsilon...">
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="card-footer text-center">
                                                <p id="msg" ></p>
                                                <button id="clusterBtn" class="btn btn-primary btn-round btn-lg"
                                                        type="submit">
                                                    <span class="now-ui-icons loader_refresh"></span>
                                                    Clustering
                                                </button>
                                            </div>
                                        </form>
<%--                                    </div>--%>
                                    <br>
                                    <div class="row">
                                        <div class="col-md-5 col-sm-5 col-5 ml-auto mr-auto">
                                            <div class="social-description text-center">
                                                <h3>${result.jmlData}</h3>
                                                <p style="margin-top: -20px">Jumlah Data</p>
                                            </div>
                                        </div>
                                        <div class="col-md-5 col-sm-5 col-5 ml-auto mr-auto">
                                            <div class="social-description text-center">
                                                <h3>${result.jmlVariable}</h3>
                                                <p style="margin-top: -20px">Jumlah Kata</p>
                                            </div>
                                        </div>
                                    </div>
                                    <br>
<%--                                    <div class="row">--%>
<%--                                        <div class="col-md-5 col-sm-5 col-5 ml-auto mr-auto">--%>
<%--                                            <div class="social-description text-center">--%>
<%--                                                <h3>${result.PCI}</h3>--%>
<%--                                                <p style="margin-top: -20px">PCI</p>--%>
<%--                                            </div>--%>
<%--                                        </div>--%>
<%--                                        <div class="col-md-5 col-sm-5 col-5 ml-auto mr-auto">--%>
<%--                                            <div class="social-description text-center">--%>
<%--                                                <h3>${result.MPCI}</h3>--%>
<%--                                                <p style="margin-top: -20px">MPCI</p>--%>
<%--                                            </div>--%>
<%--                                        </div>--%>
<%--                                    </div>--%>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3" style="padding-top: 13px">
                        <ul class="nav nav-pills nav-pills-primary flex-column" role="tablist">
                            <c:forEach items="${result.clusterResult}" var="entry">
                                <c:choose>
                                    <c:when test="${active == entry.key}">
                                        <li class="nav-item">
                                            <a class="nav-link active" href="/cluster/${entry.key}">
                                                Cluster ${entry.key}
                                            </a>
                                        </li>
                                    </c:when>
                                    <c:when test="${(active != entry.key) || (active == null)}">
                                        <li class="nav-item">
                                            <a class="nav-link" href="/cluster/${entry.key}">
                                                Cluster ${entry.key}
                                            </a>
                                        </li>
                                    </c:when>
                                </c:choose>
                            </c:forEach>
                        </ul>
                    </div>
                    <div class="col-md-9">
                        <c:choose>
                            <c:when test="${num != null}">
                                <c:forEach items="${doclist}" var="doc" varStatus="i">
                                    <div class="card card-plain card-blog">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <h5 class="card-title">
                                                        ${i.index+1}. <a href="/doc/${doc.id}"> ${doc.judul}</a>
                                                </h5>
                                                <p class="card-description">
                                                        ${doc.content} <a href="/doc/${doc.id}"> Read More </a>
                                                </p>
                                                <div class="author">
                                                    Kategori :
                                                    <span>${doc.kategori}</span>
                                                </div>
                                                <div class="author">
                                                    Cluster :
                                                    <span>${num}</span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <hr>
                                </c:forEach>
                            </c:when>
                            <c:when test="${num == null}">
                                <c:forEach items="${result.clusterResult}" var="entry" >
                                    <c:forEach items="${entry.value}" var="doc">
                                        <div class="card card-plain card-blog">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <h5 class="card-title">
                                                        <a href="/doc/${doc.id}">${doc.judul}</a>
                                                    </h5>
                                                    <p class="card-description">
                                                            ${doc.content} <a href="/doc/${doc.id}"> Read More </a>
                                                    </p>
                                                    <div class="author">
                                                        Kategori :
                                                        <span>${doc.kategori}</span>
                                                    </div>
                                                    <div class="author">
                                                        Cluster :
                                                        <span>${entry.key}</span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <hr>
                                    </c:forEach>
                                </c:forEach>
                            </c:when>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
        <!-- section -->
    </div>
</div>
<jsp:include page="footer.jsp"></jsp:include>
<script type="text/javascript">
    $(document).ready(function() {
        $('#clusterBtn').on('click', function() {
            $('#processForm').submit();
            var $this = $(this);
            var loadingText = '<span class="now-ui-icons loader_refresh spin"></span> In Process ...';
            if ($(this).html() !== loadingText) {
                $this.data('original-text', $(this).html());
                $this.html(loadingText);
            }
            $this.prop("disabled", true);
        });

        if (${isProcessing}){
            var $this = $('#clusterBtn');
            var loadingText = '<span class="now-ui-icons loader_refresh spin"></span> In Process ...';
            if ($(this).html() !== loadingText) {
                $this.data('original-text', $(this).html());
                $this.html(loadingText);
            }
            $this.prop("disabled", true);
            $('#msg').html('Proses clustering berlangsung, harap tunggu beberapa saat dan <a href="/cluster" style="color: blue" >refresh</a> halaman ini.');
        }
    });
</script>

