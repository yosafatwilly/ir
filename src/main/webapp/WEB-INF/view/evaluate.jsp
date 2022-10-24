<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp"></jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<div class="container">
    <div class="section" style="padding-top: 100px">
        <div class="container">
            <div class="col text-center">
                <h3 class="section-title">Evaluasi Sistem Pemerolehan Informasi ${type}</h3>
            </div>
            <hr>
            <p>Berikut ini ditampilkah hasil Evaluasi Information Retrieval berdasarkan <a href="/evaluation/relevan">query dan dokumen yang relevan</a>.</p>
            <p>Evaluasi yang ditampilkan yaitu Recall Precision, 11 Titik Interpolasi Recall Precision dan Visualisasi 11 Titik Interpolasi. </p>
            <hr>
            <c:forEach items="${evaluate.listRecallPrecision}" var="cat" varStatus="i">
            <div class="cd-section">
                <h4 class="title">${i.index+1}. Query "${fn:toUpperCase(cat.query)}", waktu eksekusi (${cat.time} milliseconds)</h4>
<%--                <p>Jumlah Dokumen Relevan = ${cat.relevantCount}</p>--%>
                <p>Dokumen yang relevan dengan query “${cat.query}” menurut pengguna sistem yaitu sebanyak ${cat.relevantCount} dokumen.</p>
                <div id="tables">
                    <div class="row">
                        <div class="col-md-12 ml-auto mr-auto text-center">
                            <label class="label label-success"><strong>Hasil Pencarian, Relevan, Recall dan Precision untuk Query "${fn:toUpperCase(cat.query)}" pada Sistem Pemerolehan Informasi ${type}</strong></label>
                            <div class="card card-plain">
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table class="table table-striped">
                                            <thead class="">
                                            <th>
                                                No
                                            </th>
                                            <th>
                                                ID
                                            </th>
                                            <th>
                                                Dokumen
                                            </th>
                                            <th class="text-center">
                                                Relevan
                                            </th>
                                            <th class="text-center">
                                                Recall
                                            </th>
                                            <th class="text-center">
                                                Precision
                                            </th>
                                            </thead>
                                            <tbody>
                                            <% int i = 1; %>
                                            <c:forEach items="${cat.recallPrecision}" var="rp">
                                            <tr>
                                                <td>
                                                    <%=i%>
                                                </td>
                                                <td>${rp.document.id}
                                                </td>
                                                <td>
                                                    <a href="/doc/${rp.document.id}">${rp.document.judul}</a>
                                                </td>
                                                <td class="text-center">
                                                        ${rp.relevan}
                                                </td>
                                                <td class="text-center">
                                                    <fmt:formatNumber type = "percent" minFractionDigits = "2" value = "${rp.recall}" />
                                                </td>
                                                <td class="text-center">
                                                    <fmt:formatNumber type = "percent" minFractionDigits = "2" value = "${rp.precision}" />
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
                <div id="tables">
                    <div class="row">
                        <div class="col-md-4 ml-auto mr-auto text-center">
                            <label class="label label-success"><strong>11 Titik Interpolasi Recall Precision Query "${fn:toUpperCase(cat.query)}", dari perhitungan pada tabel sebelumnya, ditampilkan pada tabel berikut ini :</strong></label>
                            <div class="card card-plain">
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table class="table table-striped">
                                            <thead class="">
                                            <th class="text-center">
                                                Recall
                                            </th>
                                            <th class="text-center">
                                                Precision
                                            </th>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${cat.interpolation}" var="inter">
                                                <tr>
                                                    <td class="text-center">
                                                        <fmt:formatNumber type = "percent" minFractionDigits = "2" value = "${inter.key}" />
                                                    </td>
                                                    <td class="text-center">
                                                        <fmt:formatNumber type = "percent" minFractionDigits = "2" value = "${inter.value}" />
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-12 text-center">
                        <label class="label label-success"><strong> Visualisasi 11 Titik Interpolasi Recall Precision untuk Query "${fn:toUpperCase(cat.query)}"</strong></label>
                        <div id="${cat.query}"></div>
                    </div>
                </div>
                <br>
                <br>
                <hr>
                </c:forEach>
                <div id="tables">
                    <div class="row">
                        <div class="col-md-4 ml-auto mr-auto text-center">
                            <label class="label label-success"><strong>Rata - Rata 11 Titik Interpolasi Recall Precision</strong></label>
                            <div class="card card-plain">
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table class="table table-striped">
                                            <thead class="">
                                            <th class="text-center">
                                                Recall
                                            </th>
                                            <th class="text-center">
                                                Precision
                                            </th>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${evaluate.interpolationAverage}" var="inter">
                                                <tr>
                                                    <td class="text-center">
                                                        <fmt:formatNumber type = "percent" minFractionDigits = "2" value = "${inter.key}" />
                                                    </td>
                                                    <td class="text-center">
                                                        <fmt:formatNumber type = "percent" minFractionDigits = "2" value = "${inter.value}" />
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                                <tr>
                                                    <td class="text-center">Average</td>
                                                    <td class="text-center"><fmt:formatNumber type = "percent" minFractionDigits = "2" value = "${evaluate.averagePrecision}"/></td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <br>
                <br>
                <hr>
                <div class="row">
                    <div class="col-sm-12 text-center">
                        <h3 class="title">Waktu Ekskusi (milliseconds)</h3>
                        <div id="time-execution"></div>
                    </div>
                </div>
                <br>
                <br>
                <hr>
                <div class="row">
                    <h3 class="section-title">Rata-rata waktu eksekusi ${evaluate.averageTime()} milliseconds</h3>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp"></jsp:include>
<script type="text/javascript">
    <c:forEach items="${evaluate.listRecallPrecision}" var="cat">
    Morris.Line({
        element: '${cat.query}',
        data: [
            {r: '0', "p": '${cat.interpolation.get(0.0) * 100}'},
            {r: '10', "p": '${cat.interpolation.get(0.1) * 100}'},
            {r: '20', "p": '${cat.interpolation.get(0.2) * 100}'},
            {r: '30', "p": '${cat.interpolation.get(0.3) * 100}'},
            {r: '40', "p": '${cat.interpolation.get(0.4) * 100}'},
            {r: '50', "p": '${cat.interpolation.get(0.5) * 100}'},
            {r: '60', "p": '${cat.interpolation.get(0.6) * 100}'},
            {r: '70', "p": '${cat.interpolation.get(0.7) * 100}'},
            {r: '80', "p": '${cat.interpolation.get(0.8) * 100}'},
            {r: '90', "p": '${cat.interpolation.get(0.9) * 100}'},
            {r: '100', "p": '${cat.interpolation.get(1.0) * 100}'}
        ],
        xkey: 'r',
        ykeys: ['p'],
        labels: ['Precision'],
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
    Morris.Bar({
        element: 'time-execution',
        data: [
            <c:forEach items="${evaluate.listRecallPrecision}" var="cat">
                {r: '${cat.query}', "time": '${cat.time}'},
            </c:forEach>
        ],
        xkey: 'r',
        ykeys: ['time'],
        labels: ['Time'],
        parseTime: false,
        hideHover: true,
        lineWidth: '1px',
        xLabelAngle: 60,
        ymin: 0,
        smooth: false,
        resize: true,
        hoverCallback: function(index, options, content) {
            return("Query:" + content + "miliseconds");
        }
    });
</script>

