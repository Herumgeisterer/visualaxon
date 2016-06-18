$.ajax("data/output.json")
    .done(function (data) {
        window.cy = cytoscape({
            container: document.getElementById('diagram'),
            style: [
                {
                    selector: 'node',
                    css: {
                        'content': 'data(name)',
                        'text-valign': 'center',
                        'text-halign': 'center'
                    }
                },
                {
                    selector: '$node > node',
                    css: {
                        'padding-top': '10px',
                        'padding-left': '10px',
                        'padding-bottom': '10px',
                        'padding-right': '10px',
                        'text-valign': 'top',
                        'text-halign': 'center',
                        'background-color': '#bbb'
                    }
                },
                {
                    selector: ':parent',
                    style: {
                        'background-opacity': 0.333
                    }
                },
                {
                    selector: 'edge',
                    style: {
                        'width': 3,
                        'line-color': '#ad1a66'
                    }
                },
                {
                    selector: '.center-center',
                    style: {
                        'text-valign': 'center',
                        'text-halign': 'center'
                    }
                }
            ],
            elements: data
        });

        window.cy.layout({
            name: 'grid',
            avoidOverlap: true,
            avoidOverlapPadding: 10,
        });
    });