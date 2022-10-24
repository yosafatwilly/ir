<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp"></jsp:include>
<div class="main">
    <div class="section" style="padding-top: 100px">
        <div class="container">
            <div class="row">
                <div class="col-md-8 ml-auto mr-auto">
                    <h3 class="title">${document.judul}</h3>
                    <h6>Kategori : ${document.kategori}</h6>
                    <hr>
                    <p><strong>${document.fullContent}</strong></p>
                </div>
            </div>
            <div class="row">
                <div class="col-md-8 ml-auto mr-auto">
                    <button class="btn btn-primary btn-simple btn-round" type="button" onclick="window.history.back()">
                        <i class="now-ui-icons arrows-1_minimal-left"></i> Back
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="footer.jsp"></jsp:include>
