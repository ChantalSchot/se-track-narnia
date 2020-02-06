const api = "http://localhost:8080/api/visitors";
var visitorTable;
var narniaTable;

$(document).ready(function() {
    initVisitorTable();
    initNarniaTable();

    getVisitorData();
    getNarniaData();

    // Select/deselect rows in visitor table
    $("tbody").on( 'click', 'tr', function () {
        if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        }
        else {
            visitorTable.$('tr.selected').removeClass('selected');
            narniaTable.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
    });

    // Select/deselect rows in Narnia table
    // $("#narniaTable tbody").on( 'click', 'tr', function () {
    //     if ( $(this).hasClass('selected') ) {
    //         $(this).removeClass('selected');
    //     }
    //     else {
    //         narniaTable.$('tr.selected').removeClass('selected');
    //         $(this).addClass('selected');
    //     }
    // });

    $("#fetchVisitorTable").click(function() {
        getVisitorData();
    });
    $("#fetchNarniaTable").click(function() {
        getNarniaData();
    });

    $("#enterNarnia").click(function() {
        if (visitorTable.row($('.selected')).data() != undefined) {
            enterNarnia(visitorTable.row($('.selected')).data())
        } else {
            // no visitor selected modal
        }
    });

    $("#exitNarnia").click(function() {
        if (visitorTable.row($('.selected')).data() != undefined) {
            exitNarnia(visitorTable.row($('.selected')).data())
        } else if (narniaTable.row($('.selected')).data() != undefined) {
            exitNarnia(narniaTable.row($('.selected')).data())
        } else {
            // no visitor selected modal
        }
    })


});

function initVisitorTable() {
// Create columns (with titles) for datatable: id, firstName, lastName, age, city, inNarnia
    columns = [
        { "title":  "Visitor ID",
            "data": "id" },
        { "title":  "First Name",
            "data": "firstName" },
        { "title":  "Last Name",
            "data": "lastName" },
        { "title": "Age",
            "data": "age"},
        { "title":  "City",
            "data": "city"},
        { "title": "In Narnia?",
            "data": "inNarnia",
            "render": function(inNarnia) {
                if (inNarnia == true) {
                    return "Yes"
                } else {
                    return "No"
                }
            }}
    ];

    // Define new table with above columns
    visitorTable = $("#visitorTable").DataTable( {
        "order": [[ 0, "asc" ]],
        "columns": columns
    });
}

function initNarniaTable() {
// Create columns (with titles) for datatable: id, firstName, lastName, age, city, inNarnia
    columns = [
        { "title":  "Visitor ID",
            "data": "id" },
        { "title":  "First Name",
            "data": "firstName" },
        { "title":  "Last Name",
            "data": "lastName" },
        { "title": "Age",
            "data": "age"},
        { "title":  "City",
            "data": "city"},
        { "title": "In Narnia?",
            "data": "inNarnia",
            "render": function(inNarnia) {
                if (inNarnia == true) {
                    return "Yes"
                } else {
                    return "No"
                }
            }}
    ];

    // Define new table with above columns
    narniaTable = $("#narniaTable").DataTable( {
        "order": [[ 0, "asc" ]],
        "columns": columns
    });
}

function getVisitorData() {
    $.get(
        {
            url: api,
            dataType: "json",
            success: function (data) {
                if (data) {
                    visitorTable.clear();
                    visitorTable.rows.add(data);
                    visitorTable.columns.adjust().draw();
                }
            }
        }
    );
}

function getNarniaData() {
    $.get(
        {
            url: api+"/narnia",
            dataType: "json",
            success: function (data) {
                if (data) {
                    narniaTable.clear();
                    narniaTable.rows.add(data);
                    narniaTable.columns.adjust().draw();
                }
            }
        }
    );
}

function enterNarnia(visitor) {
    var jsonObject = JSON.stringify(visitor);

    $.ajax({
        url: api + "/narnia/enter",
        type: "put",
        dataType: "json",
        data: jsonObject,
        contentType: "application/json",
        success: function(result) {
            $("#responseText").html(result.toString());
            // showResult(result);
            getVisitorData();
            getNarniaData();
            console.log()
        },
        error: function (error) {
            console.log(error);
            getVisitorData();
            getNarniaData();
        }
    });
}

function exitNarnia(visitor) {
    var jsonObject = JSON.stringify(visitor);

    $.ajax({
        url: api + "/narnia/exit",
        type: "put",
        dataType: "json",
        data: jsonObject,
        contentType: "application/json",
        success: function(result) {
            $("#responseText").html(result.toString());
            // showResult(result);
            getVisitorData();
            getNarniaData();
        },
        error: function (error) {
            console.log(error);
            getVisitorData();
            getNarniaData();
        }
    });
}

function showResult(result) {
    $("#responseText").html(result.toString());
    console.log("update successfull");
}