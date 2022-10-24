<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp"></jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="container">
    <div class="section" style="padding-top: 100px">
        <div class="container">
            <div class="col text-center">
                <h3 class="section-title">Perbandingan 11 Titik Interpolasi Recall Precision pada Sistem Pemerolehan Berbasis Cluster dan Tanpa Cluster</h3>
            </div>
            <hr>
                <c:forEach items="${ev.get(0).listRecallPrecision}" var="cat">
                    <div class="cd-section">
                        <h3 class="title">Query "${fn:toUpperCase(cat.query)}"</h3>
                        <div class="row">
                            <div class="col-sm-12 text-center">
                                <label class="label label-success"><strong>Visualisasi 11 Titik Interpolasi Recall Precision untuk Query "${fn:toUpperCase(cat.query)}"</strong></label>
                                <div id="${cat.query}"></div>
                            </div>
                        </div>
                        <br>
                        <br>
                        <hr>
                    </div>
                </c:forEach>
                <div class="row">
                    <div class="col-sm-12 text-center">
                        <h3 class="title">Waktu Eksekusi (milliseconds) - Bar Chart</h3>
                        <p>Semakin rendah bar, maka semakin baik.</p>
                        <div id="time-execution-bar"></div>
                    </div>
                </div>
                <br><br><hr>
                <div class="row">
                    <div class="col-sm-12 text-center">
                        <h3 class="title">Waktu Eksekusi (milliseconds) - Area Chart</h3>
                        <p>Semakin sempit area, maka semakin baik.</p>
                        <div id="time-execution-area"></div>
                    </div>
                </div>
                <br><br><hr>
                <div class="row">
                    <div class="col-sm-12 text-center">
                        <h3 class="title">Waktu Eksekusi (milliseconds) - Line Chart</h3>
                        <p>Makin rendah garis, maka semakin baik.</p>
                        <div id="time-execution-line"></div>
                    </div>
                </div>
                <br><br><hr>
                <div class="row">
                    <div class="col-sm-12 text-center">
                        <h3 class="title">Rata-rata Waktu Eksekusi</h3>
                        <p>Konvensional : ${ev.get(0).averageTime()} milliseconds</p>
                        <p>Berbasis Clustering : ${ev.get(1).averageTime()} milliseconds</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp"></jsp:include>

<script type="text/javascript">
        <c:forEach items="${ev.get(0).listRecallPrecision}" var="cat" varStatus="i">
        Morris.Line({
            element: '${cat.query}',
            data: [
                {r: '0', "noncluster": '${cat.interpolation.get(0.0) * 100}', "cluster": '${ev.get(1).listRecallPrecision.get(i.index).interpolation.get(0.0) * 100}'},
                {r: '10', "noncluster": '${cat.interpolation.get(0.1) * 100}', "cluster": '${ev.get(1).listRecallPrecision.get(i.index).interpolation.get(0.1) * 100}'},
                {r: '20', "noncluster": '${cat.interpolation.get(0.2) * 100}', "cluster": '${ev.get(1).listRecallPrecision.get(i.index).interpolation.get(0.2) * 100}'},
                {r: '30', "noncluster": '${cat.interpolation.get(0.3) * 100}', "cluster": '${ev.get(1).listRecallPrecision.get(i.index).interpolation.get(0.3) * 100}'},
                {r: '40', "noncluster": '${cat.interpolation.get(0.4) * 100}', "cluster": '${ev.get(1).listRecallPrecision.get(i.index).interpolation.get(0.4) * 100}'},
                {r: '50', "noncluster": '${cat.interpolation.get(0.5) * 100}', "cluster": '${ev.get(1).listRecallPrecision.get(i.index).interpolation.get(0.5) * 100}'},
                {r: '60', "noncluster": '${cat.interpolation.get(0.6) * 100}', "cluster": '${ev.get(1).listRecallPrecision.get(i.index).interpolation.get(0.6) * 100}'},
                {r: '70', "noncluster": '${cat.interpolation.get(0.7) * 100}', "cluster": '${ev.get(1).listRecallPrecision.get(i.index).interpolation.get(0.7) * 100}'},
                {r: '80', "noncluster": '${cat.interpolation.get(0.8) * 100}', "cluster": '${ev.get(1).listRecallPrecision.get(i.index).interpolation.get(0.8) * 100}'},
                {r: '90', "noncluster": '${cat.interpolation.get(0.9) * 100}', "cluster": '${ev.get(1).listRecallPrecision.get(i.index).interpolation.get(0.9) * 100}'},
                {r: '100', "noncluster": '${cat.interpolation.get(1.0) * 100}', "cluster": '${ev.get(1).listRecallPrecision.get(i.index).interpolation.get(1.0) * 100}'},
            ],
            xkey: 'r',
            ykeys: ['noncluster', 'cluster'],
            labels: ['Tanpa Cluster', 'Berbasis Cluster'],
            parseTime: false,
            hideHover: true,
            lineWidth: '1px',
            stacked: true,
            ymin: 0,
            ymax: 100,
            resize: true,
            smooth: false,
            hoverCallback: function(index, options, content) {
                return("Recall:" + content);
            }
        });
        </c:forEach>
</script>

<script type="text/javascript">
    var data = [
            <c:forEach items="${ev.get(0).listRecallPrecision}" var="cat" varStatus="i">
                {r: '${cat.query}', "noncluster": '${cat.time}', "cluster":'${ev.get(1).listRecallPrecision.get(i.index).time}'},
            </c:forEach>
        ],
        config = {
            data: data,
            xkey: 'r',
            ykeys: ['noncluster', 'cluster'],
            labels: ['Tanpa Cluster', 'Berbasis Cluster'],
            parseTime: false,
            hideHover: true,
            lineWidth: '1px',
            xLabelAngle: 60,
            ymin: 0,
            smooth: false,
            resize: true
        };
    config.element = 'time-execution-area';
    Morris.Area(config);
    config.element = 'time-execution-line';
    Morris.Line(config);
    config.element = 'time-execution-bar';
    Morris.Bar(config);
</script>