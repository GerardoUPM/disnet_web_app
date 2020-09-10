$("#search-by-disease-button").on('click', () => {
    new Promise((resolve) => {
        resolve(
            $("#select-search-method").velocity({
                opacity: 0.1,
                translateX: "-100%",
                duration: 50,
                complete: () => {
                        $("#search-by-disease").show()
                        $("#search-by-disease").velocity({
                            translateX: "0%",
                            opacity: 1,
                            duration: 50
                        })
                }
            })
        )
    })

        .then(() => $("#select-search-method").hide(100, 'linear'))

})

$("#search-by-symptom-button").on('click', () => {
    new Promise((resolve) => {
        resolve(
            $("#select-search-method").velocity({
                opacity: 0.1,
                translateX: "-100%",
                duration: 50,
                complete: () => {
                    $("#search-by-symptom").show()
                    $("#search-by-symptom").velocity({
                        translateX: "0%",
                        opacity: 1,
                        duration: 50
                    })
                }
            })
        )
    })

        .then(() => $("#select-search-method").hide(100, 'linear'))
})