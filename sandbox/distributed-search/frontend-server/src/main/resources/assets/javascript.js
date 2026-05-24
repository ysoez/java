$(document).ready(function() {
    var button         = $("#submit_button");
    var searchBox      = $("#search_text");
    var numResultsBox  = $("#num_results");
    var minScoreBox    = $("#min_score");
    var resultsTable   = $("#results table tbody");
    var resultsWrapper = $("#results");
    var noResultsError = $("#no_results_error");
    var resultsCount   = $("#results_count");

    searchBox.on("keydown", function(e) {
        if (e.key === "Enter") button.trigger("click");
    });

    button.on("click", function() {
        button.addClass("loading").prop("disabled", true);
        $.ajax({
            method: "POST",
            contentType: "application/json",
            data: createRequest(),
            url: "search",
            dataType: "json",
            success: onHttpResponse,
            error: function(xhr, status) {
                alert("Error connecting to the server: " + status);
            },
            complete: function() {
                button.removeClass("loading").prop("disabled", false);
            }
        });
    });

    function createRequest() {
        var minScore = parseFloat(minScoreBox.val());
        if (isNaN(minScore)) minScore = 0;

        var maxNumberOfResults = parseInt(numResultsBox.val());
        if (isNaN(maxNumberOfResults)) maxNumberOfResults = Number.MAX_SAFE_INTEGER;

        return JSON.stringify({
            search_query: searchBox.val(),
            min_score: minScore,
            max_number_of_results: maxNumberOfResults
        });
    }

    function onHttpResponse(data, status) {
        if (status === "success") {
            addResults(data);
        } else {
            alert("Error connecting to the server: " + status);
        }
    }

    function addResults(data) {
        var baseDir = data.documents_location;
        resultsTable.empty();

        if (data.search_results.length === 0) {
            resultsWrapper.removeClass("visible");
            noResultsError.addClass("visible");
        } else {
            noResultsError.removeClass("visible");
            resultsCount.text(data.search_results.length + " found");
            resultsWrapper.addClass("visible");
        }

        data.search_results.forEach(function(item, i) {
            var fullPath = baseDir + "/" + item.title + "." + item.extension;
            var row = $("<tr>").css("animation-delay", (i * 0.05) + "s").append(
                $("<td>").append($("<a>").attr("href", fullPath).text(item.title)),
                $("<td>").append($("<span>").addClass("score-badge").text(
                    item.score.toFixed ? item.score.toFixed(3) : item.score
                ))
            );
            resultsTable.append(row);
        });
    }
});