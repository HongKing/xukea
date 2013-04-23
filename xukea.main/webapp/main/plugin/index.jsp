<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" errorPage="" %>
<%@ include file="/commons/taglibs.jsp" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>插件管理</title>
    <%@ include file="/commons/meta.jsp" %>
    
</head>
<body >
    <%@ include file="/commons/header.jsp" %>
    
    <section id="secondary_bar">
        <div class="user">
            <p>John Doe (<a href="#">3 Messages</a>)</p>
            <!-- <a class="logout_user" href="#" title="Logout">Logout</a> -->
        </div>
        <div class="breadcrumbs_container">
            <article class="breadcrumbs">
                <a href="index.html">Website Admin</a> 
                <div class="breadcrumb_divider"></div> 
                <a class="current">Dashboard</a>
            </article>
        </div>
    </section><!-- end of secondary bar -->
    
    <aside id="sidebar" class="column">
        <form class="quick_search">
            <input type="text" value="Quick Search" onfocus="if(!this._haschanged){this.value=''};this._haschanged=true;">
        </form>
        <hr/>
        <h3>Content</h3>
        <ul class="toggle">
            <li class="icn_new_article"><a href="#">New Article</a></li>
            <li class="icn_edit_article"><a href="#">Edit Articles</a></li>
            <li class="icn_categories"><a href="#">Categories</a></li>
            <li class="icn_tags"><a href="#">Tags</a></li>
        </ul>
        <h3>Users</h3>
        <ul class="toggle">
            <li class="icn_add_user"><a href="#">Add New User</a></li>
            <li class="icn_view_users"><a href="#">View Users</a></li>
            <li class="icn_profile"><a href="#">Your Profile</a></li>
        </ul>
        <h3>Media</h3>
        <ul class="toggle">
            <li class="icn_folder"><a href="#">File Manager</a></li>
            <li class="icn_photo"><a href="#">Gallery</a></li>
            <li class="icn_audio"><a href="#">Audio</a></li>
            <li class="icn_video"><a href="#">Video</a></li>
        </ul>
        <h3>Admin</h3>
        <ul class="toggle">
            <li class="icn_settings"><a href="#">Options</a></li>
            <li class="icn_security"><a href="#">Security</a></li>
            <li class="icn_jump_back"><a href="#">Logout</a></li>
        </ul>
        
        <footer>
            <hr />
            <p><strong>Copyright &copy; 2011 Website Admin</strong></p>
            <p>Theme by <a href="http://www.medialoot.com">MediaLoot</a></p>
        </footer>
    </aside><!-- end of sidebar -->
    
        <div class="module_box">
            <div class="header"><h3>Basic Styles</h3></div>
            <div class="content">
                <h1>Header 1</h1>
                <h2>Header 2</h2>
                <h3>Header 3</h3>
                <h4>Header 4</h4>
                <p>Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Cras mattis consectetur purus sit amet fermentum. Maecenas faucibus mollis interdum. Maecenas faucibus mollis interdum. Cras justo odio, dapibus ac facilisis in, egestas eget quam.</p>
                <p>Donec id elit non mi porta <a href="#">link text</a> gravida at eget metus. Donec ullamcorper nulla non metus auctor fringilla. Cras mattis consectetur purus sit amet fermentum. Aenean eu leo quam. Pellentesque ornare sem lacinia quam venenatis vestibulum.</p>
                <ul>
                    <li>Donec ullamcorper nulla non metus auctor fringilla. </li>
                    <li>Cras mattis consectetur purus sit amet fermentum.</li>
                    <li>Donec ullamcorper nulla non metus auctor fringilla. </li>
                    <li>Cras mattis consectetur purus sit amet fermentum.</li>
                </ul>
            </div>
            <div class="footer"></div>
        </div><!-- end of styles article -->
    
        
    <%@ include file="/commons/footer.jsp" %>
</body>
</html>