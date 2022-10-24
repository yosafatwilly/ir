<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <link rel="icon" type="image/png" href="/assets/img/logo25px.png">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <title>${title != null ? title.concat(" - ") : ""} Information Retrieval</title>
    <meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0, shrink-to-fit=no'
          name='viewport'/>

    <!--     Fonts and icons     -->
    <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700,200" rel="stylesheet" />
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css" />

    <!-- CSS Files -->
    <link href="/assets/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="/assets/css/now-ui-kit.css?v=1.1.0" rel="stylesheet"/>

    <link type="text/css" rel="stylesheet" href="/assets/css/style.css"/>
    <link type="text/css" rel="stylesheet" href="/assets/css/error.css"/>
    <link type="text/css" rel="stylesheet" href="/assets/css/morris.0.5.1.css"/>


</head>
<body id="body">
<nav class="navbar navbar-expand-lg bg-white fixed-top ">
    <div class="container">
        <div class="navbar-translate">
            <a class="navbar-brand" href="/" rel="tooltip" data-placement="bottom">
                <img src="/assets/img/logo-nav.png" height="17" style="margin-top: -5px;">
                <strong>&nbsp;Information Retrieval</strong>
            </a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navigation"
                    aria-controls="navigation-index" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-bar bar1"></span>
                <span class="navbar-toggler-bar bar2"></span>
                <span class="navbar-toggler-bar bar3"></span>
            </button>
        </div>

        <div class="collapse navbar-collapse" data-color="orange">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <a class="nav-link" href="/docs">
                        <i class="now-ui-icons files_paper"></i>
                        <strong>Documents</strong>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/cluster">
                        <i class="now-ui-icons design_vector"></i>
                        <strong>Clustering</strong>
                    </a>
                </li>
                <li class="nav-item dropdown">
                    <a href="#" class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" data-toggle="dropdown">
                        <i class="now-ui-icons business_chart-bar-32" aria-hidden="true"></i>
                        <strong>Evaluasi IR</strong>
                    </a>
                    <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdownMenuLink">
                        <a class="dropdown-item" href="/evaluation/relevan">
                            <i class="now-ui-icons business_bulb-63"></i><strong>Query & Relevant
                            Documents</strong>
                        </a>
                        <a class="dropdown-item" href="/evaluation/fcm">
                            <i class="now-ui-icons design_vector"></i><strong>Evaluasi IR Berbasis Cluster</strong>
                        </a>
                        <a class="dropdown-item" href="/evaluation/tfidf">
                            <i class="now-ui-icons business_globe"></i><strong>Evaluasi IR Tanpa Cluster</strong>
                        </a>
                        <a class="dropdown-item" href="/evaluation/compare">
                            <i class="now-ui-icons media-2_sound-wave"></i><strong>Compare</strong>
                        </a>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</nav>
<div class="wrapper">
