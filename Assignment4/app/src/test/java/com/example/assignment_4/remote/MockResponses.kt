package com.example.assignment_4.remote

object MockResponses {
    val topAnimeResponse = """
    {
        "data": [
            {
                "mal_id": 1,
                "title": "Cowboy Bebop",
                "synopsis": "In the year 2071...",
                "images": {
                    "jpg": {
                        "image_url": "https://example.com/image.jpg"
                    }
                }
            },
            {
                "mal_id": 2,
                "title": "Fullmetal Alchemist: Brotherhood",
                "synopsis": "Two brothers...",
                "images": {
                    "jpg": {
                        "image_url": "https://example.com/image2.jpg"
                    }
                }
            }
        ]
    }
    """.trimIndent()

    val animeDetailResponse = """
    {
        "data": {
            "mal_id": 1,
            "title": "Cowboy Bebop",
            "synopsis": "In the year 2071...",
            "images": {
                "jpg": {
                    "image_url": "https://example.com/image.jpg"
                }
            }
        }
    }
    """.trimIndent()
}
