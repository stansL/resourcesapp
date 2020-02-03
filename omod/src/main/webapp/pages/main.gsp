<%
    ui.decorateWith("appui", "standardEmrPage", [title: "Medical Resources"]);

    ui.includeJavascript("patientdashboardapp", "jq.print.js")
    ui.includeJavascript("patientdashboardapp", "jq.slimscroll.js")

    ui.includeCss("patientdashboardapp", "patientdashboardapp.css");
%>
<script>
    var confirmdialog;

    jq(document).ready(function () {
        confirmdialog = emr.setupConfirmationDialog({
            dialogOpts: {
                overlayClose: false,
                close: true
            },
            selector: '#confirmDialog',
            actions: {
                confirm: function () {
                    confirmdialog.close();
                    window.location.href = '${ui.pageLink("patientqueueapp", "opdQueue", [app: "patientdashboardapp.opdqueue"])}';
                },
                cancel: function () {
                    confirmdialog.close();
                }
            }
        });

        jq(".dashboard-tabs").tabs();
        jq(".cancelButton").on("click", function (e) {
            e.preventDefault();
            confirmdialog.show();

        });

        var stuff = ${files.collect { it.toJson() }};

        documentsTable('#docsTable', stuff);
        jq('.toLink').each(function () {
            var t = jq(this);
            t.on("click", function () {
                // alert(t.data('title'));
                // console.log(t.data('title'));
                console.log("About to Post");
                emr.fragmentActionLink("resourcesapp", "resources","loadStuff", { "fileName": t.data('title')});
                console.log("just posted ......");

            });
        });


    });


    function documentsTable(selector, data) {
        for (var i = 0; i < data.length; i++) {

            var tested = data[i].displayName;
            var row = jq('<tr/>');
            row.append(jq('<td/>').html((i + 1)));
            var cstyle = data[i].icon + " small";
            row.append(jq('<td/>').html('<i class="' + cstyle + '"></i><strong>' + tested + '</strong><br/>' + data[i].shortDescription));
            row.append(jq('<td/>').html(data[i].version));


            // Create anchor element.
            var a = document.createElement('a');
            a.classList.add("toLink");

            a.setAttribute('data-title', tested);

            // Create the text node for anchor element.
            var link = document.createElement('i');
            link.classList.add("small");
            link.classList.add("icon-download-alt");
            link.classList.add("link");

            // Append the text node to anchor element.
            a.append(link);

            // Append the anchor element to the table
            row.append(jq('<td/>').html(a));
            jq(selector).append(row);
        }
    }


</script>

<style>
.name {
    color: #f26522;
}

input[type="text"],
input[type="password"],
select {
    border: 1px solid #aaa;
    border-radius: 2px !important;
    box-shadow: none !important;
    box-sizing: border-box !important;
    height: 38px !important;
    line-height: 18px !important;
    padding: 0px 10px !important;
    width: 100% !important;
}

input[type="text"]:focus, textarea:focus {
    outline: 2px none #007fff !important;
    box-shadow: 0 0 2px 0 #888 !important
}

textarea {
    width: 97%;
}

.append-to-value {
    color: #999;
    float: right;
    left: auto;
    margin-left: -50px;
    margin-top: 5px;
    padding-right: 10px;
    position: relative;
}

form h2 {
    margin: 10px 0 0;
    padding: 0 5px
}

.col1, .col2, .col3, .col4, .col5, .col6, .col7, .col8, .col9, .col10, .col11, .col12 {
    float: left;
}

form label, .form label {
    margin: 5px 0 0;
    padding: 0 5px
}

#datetime label {
    display: none;
}

.add-on {
    float: right;
    left: auto;
    margin-left: -29px;
    margin-top: 10px;
    position: absolute;
}

.dashboard .info-section {
    margin: 0 5px 5px;
}

.dashboard .info-body li {
    padding-bottom: 2px;
}

.dashboard .info-body li span {
    margin-right: 10px;
}

.dashboard .info-body li div {
    width: 150px;
    display: inline-block;
}

.info-body ul li {
    display: none;
}

.simple-form-ui section.focused {
    width: 75%;
}

.new-patient-header .demographics .gender-age {
    font-size: 14px;
    margin-left: -55px;
    margin-top: 12px;
}

.new-patient-header .demographics .gender-age span {
    border-bottom: 1px none #ddd;
}

.new-patient-header .identifiers {
    margin-top: 5px;
}

.tag {
    padding: 2px 10px;
}

.tad {
    background: #666 none repeat scroll 0 0;
    border-radius: 1px;
    color: white;
    display: inline;
    font-size: 0.8em;
    padding: 2px 10px;
}

.status-container {
    padding: 5px 10px 5px 5px;
}

.catg {
    color: #363463;
    margin: 25px 10px 0 0;
}

.ui-tabs {
    margin-top: 5px;
}

.simple-form-ui section.focused {
    width: 74.6%;
    min-height: 400px;
}

.no-confirmation {
    margin-top: -10px;
}

.no-confirmation .label {
    color: #009384;
    cursor: pointer;
    font-size: 1.3em;
    font-weight: normal;
    margin: 10px 0 0;
    padding: 0 5px;
}

.col15 {
    min-width: 22%;
    max-width: 22%;
    float: left;
    display: inline-block;
}

.col16 {
    display: inline-block;
    float: left;
    width: 730px;
}

.important {
    color: #f00 !important;
}

#breadcrumbs a, #breadcrumbs a:link, #breadcrumbs a:visited {
    text-decoration: none;
}

#summaryTable tr:nth-child(2n),
#summaryTable tr:nth-child(2n+1) {
    background: none;
}

#summaryTable {
    margin: -5px 0 -6px 0px;
}

#summaryTable tr,
#summaryTable th,
#summaryTable td {
    border: 1px none #eee;
    border-bottom: 1px solid #eee;
}

#summaryTable td:first-child {
    vertical-align: top;
    width: 170px;
}

.result-title {
    display: inline-block;
    float: left;
    margin: 0 60px 0 0;
}

.result-page {
    display: inline-block;
}

.result-page i {
    color: #aaa;
}

#person-detail {
    display: none;
}

#modal-overlay {
    background: #000 none repeat scroll 0 0;
    opacity: 0.4 !important;
}

.red {
    border: 1px solid #f00 !important;
}

#cancelButton {
    margin-left: 5px;
}
</style>

<openmrs:require privilege="Triage Queue" otherwise="/login.htm"
                 redirect="/module/patientqueueapp/queue.page?app=patientdashboardapp.triage"/>
<openmrs:globalProperty key="hospitalcore.hospitalName" defaultValue="ddu" var="hospitalName"/>

<div class="clear"></div>

<div id="content">
    <div class="example">
        <ul id="breadcrumbs">
            <li>
                <a href="${ui.pageLink('referenceapplication', 'home')}">
                    <i class="icon-home small"></i></a>
            </li>
            <li>
                <i class="icon-chevron-right link"></i>
                <a href="${ui.pageLink('patientqueueapp', 'opdQueue', [app: 'patientdashboardapp.opdqueue'])}">Medical Resources</a>
            </li>

        </ul>
    </div>

    <div>
        Medical Documents <br/>
    </div>
    <table id="docsTable">
        <thead>
        <tr>
            <th>#</th>
            <th>File</th>
            <th>Version</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>

        </tbody>
    </table>

</div>


