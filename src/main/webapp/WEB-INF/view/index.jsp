<%--
  Created by IntelliJ IDEA.
  User: yosaf
  Date: 5/22/2019
  Time: 9:18 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp"></jsp:include>
<div class="main">
    <div class="container" style="padding-top: 115px; padding-bottom: 120px">
        <div class="section">
            <h1 class="title text-center">
                <span style="color: #4285F4; margin-left: -15px;">I</span>
                <span style="color: #DB4437; margin-left: -15px;">n</span>
                <span style="color: #F4B400; margin-left: -15px;">f</span>
                <span style="color: #4285F4; margin-left: -15px;">o</span>
                <span style="color: #0F9D58; margin-left: -15px;">r</span>
                <span style="color: #DB4437; margin-left: -15px;">m</span>
                <span style="color: #4285F4; margin-left: -15px;">a</span>
                <span style="color: #DB4437; margin-left: -15px;">t</span>
                <span style="color: #F4B400; margin-left: -15px;">i</span>
                <span style="color: #4285F4; margin-left: -15px;">o</span>
                <span style="color: #0F9D58; margin-left: -15px;">n</span>

                <br>
                <span style="color: #4285F4; margin-left: -15px;">R</span>
                <span style="color: #DB4437; margin-left: -15px;">e</span>
                <span style="color: #F4B400; margin-left: -15px;">t</span>
                <span style="color: #4285F4; margin-left: -15px;">r</span>
                <span style="color: #0F9D58; margin-left: -15px;">i</span>
                <span style="color: #DB4437; margin-left: -15px;">e</span>
                <span style="color: #4285F4; margin-left: -15px;">v</span>
                <span style="color: #DB4437; margin-left: -15px;">a</span>
                <span style="color: #F4B400; margin-left: -15px;">l</span>
            </h1>
            <br/>
            <div class="row">
                <div class="col-lg-6 col-md-10 col-sm-12 col-12 ml-auto mr-auto">
                    <form autocomplete="off" class="form" id="search" method="POST" action="/search">

                        <div id="inputSearch" class="input-group form-group-no-border input-lg">
                            <input type="text" name="query" required="true" class="form-control" placeholder="Search">
                            <span class="input-group-addon" onclick="submitForm();">
                                <i class="now-ui-icons ui-1_zoom-bold"></i>
                            </span>
                        </div>

<%--                        Disabled--%>
<%--                        <div id="inputSearch" class="input-group form-group-no-border input-lg">--%>
<%--                            <input type="text" name="query" disabled required="true" class="form-control" placeholder="Search">--%>
<%--                                <span class="input-group-addon">--%>
<%--                                    <i class="now-ui-icons ui-1_zoom-bold"></i>--%>
<%--                                </span>--%>
<%--                        </div>--%>
<%--                        End Disabled--%>

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
            </div>
        </div>
    </div>
</div>
</div>
</body>
<script type="text/javascript">
    function submitForm() {
        document.getElementById('search').submit();
    }
</script>
<!-- Core JS Files -->
<script src="/assets/js/core/jquery.3.2.1.min.js" type="text/javascript"></script>
<script src="/assets/js/core/popper.min.js" type="text/javascript"></script>
<script src="/assets/js/core/bootstrap.min.js" type="text/javascript"></script>
<script src="/assets/js/now-ui-kit.js?v=1.1.0" type="text/javascript"></script>
</html>
