package ru.otus.cookbook.data

/**
 * Recipe data class
 */
data class Recipe(
    val id: Int,
    val title: String,
    val description: String,
    val category: RecipeCategory,
    val imageUrl: String,
    val steps: List<String>
)

/**
 * Sample recipes
 */
val cookbook = listOf(
    Recipe(
        id = 1,
        title = "Pasta Carbonara",
        description = "Pasta Carbonara is a classic pasta dish from Rome with eggs, cheese and bacon.",
        category = RecipeCategory("Pasta"),
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/3/33/Espaguetis_carbonara.jpg",
        steps = listOf(
            "Cook the pasta in a large pot of boiling salted water until al dente.",
            "Meanwhile, whisk the eggs and Parmesan together in a bowl.",
            "Heat the oil in a large pan over medium heat.",
            "Add the pancetta and cook until golden and crisp.",
            "Add the garlic and cook for 1 minute.",
            "Drain the pasta, reserving 1 cup of the cooking water.",
            "Add the pasta to the pan with the pancetta and garlic and toss to combine.",
            "Remove the pan from the heat and add the egg mixture, tossing to combine.",
            "Add the reserved pasta water, a little at a time, until the sauce is creamy.",
            "Season with salt and pepper and serve with extra Parmesan."
        )
    ),
    Recipe(
        id = 2,
        title = "Fettuccine_Alfredo",
        description = "Fettuccine Alfredo is a classic pasta dish from Rome with butter, cream and Parmesan.",
        category = RecipeCategory("Pasta"),
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/d/dc/The_Only_Original_Alfredo_Sauce_with_Butter_and_Parmesano-Reggiano_Cheese.png",
        steps = listOf(
            "Cook the fettuccine in a large pot of boiling salted water until al dente.",
            "Meanwhile, melt the butter in a large pan over medium heat.",
            "Add the cream and cook until heated through.",
            "Add the Parmesan and cook until melted.",
            "Drain the fettuccine, reserving 1 cup of the cooking water.",
            "Add the fettuccine to the pan with the sauce and toss to combine.",
            "Remove the pan from the heat and add the reserved pasta water, tossing to combine.",
            "Season with salt and pepper and serve with extra Parmesan."
        )
    ),
    Recipe(
        id = 3,
        title = "Cod in breadcrumbs",
        description = "Cod in breadcrumbs is a simple and delicious fish dish that is perfect for a quick and easy dinner.",
        category = RecipeCategory("Fish"),
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/64/Fishfinger_classic_fried_2.jpg/2560px-Fishfinger_classic_fried_2.jpg",
        steps = listOf(
            "Preheat the oven to 200C/180C Fan/Gas 6.",
            "Place the cod fillets on a baking tray.",
            "Mix the breadcrumbs, parsley, lemon zest and olive oil together in a bowl.",
            "Season the cod fillets with salt and pepper.",
            "Spread the breadcrumb mixture over the cod fillets.",
            "Bake in the oven for 15-20 minutes, or until the fish is cooked through and the breadcrumbs are golden brown."
        )
    ),
    Recipe(
        id = 4,
        title = "Beef Stroganoff",
        description = "Beef Stroganoff is a classic Russian dish made with beef, mushrooms and sour cream.",
        category = RecipeCategory("Beef"),
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/95/Moscow_%288351271825%29.jpg/500px-Moscow_%288351271825%29.jpg",
        steps = listOf(
            "Heat the oil in a large pan over medium heat.",
            "Add the beef and cook until browned.",
            "Add the onion and mushrooms and cook until softened.",
            "Add the garlic and cook for 1 minute.",
            "Add the paprika and cook for 1 minute.",
            "Add the stock and bring to a simmer.",
            "Stir in the sour cream and cook for 5 minutes, or until the sauce has thickened.",
            "Season with salt and pepper and serve with rice or noodles."
        )
    ),
    Recipe(
        id = 5,
        title = "Chicken Tikka Masala",
        description = "Chicken Tikka Masala is a classic Indian dish made with marinated chicken cooked in a spicy tomato sauce.",
        category = RecipeCategory("Chicken"),
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/f/fd/Chicken_tikka_masala.jpg",
        steps = listOf(
            "Marinate the chicken in the yogurt, lemon juice, garlic, ginger, garam masala, turmeric, cumin, coriander, paprika, salt and pepper for at least 1 hour.",
            "Heat the oil in a large pan over medium heat.",
            "Add the chicken and cook until browned.",
            "Add the onion, garlic and ginger and cook until softened.",
            "Add the tomato paste, garam masala, turmeric, cumin, coriander, paprika, salt and pepper and cook for 1 minute.",
            "Add the tomatoes and bring to a simmer.",
            "Stir in the cream and cook for 5 minutes, or until the sauce has thickened.",
            "Season with salt and pepper and serve with rice and naan bread."
        )
    ),
    Recipe(
        id = 6,
        title = "Solyanka",
        description = "Solyanka is a traditional Russian soup made with meat, pickles, olives and capers.",
        category = RecipeCategory("Soup"),
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/a/ab/Soljanka_food_05.jpg",
        steps = listOf(
            "Heat the oil in a large pan over medium heat.",
            "Add the onion, garlic, carrots and celery and cook until softened.",
            "Add the beef and cook until browned.",
            "Add the tomatoes, pickles, olives, capers, bay leaves, paprika, salt and pepper and bring to a simmer.",
            "Stir in the stock and bring to a boil.",
            "Reduce the heat and simmer for 1 hour.",
            "Season with salt and pepper and serve with sour cream and rye bread."
        )
    ),
    Recipe(
        id = 7,
        title = "Surströmming",
        description = "Surströmming is a traditional Swedish dish made with fermented herring.",
        category = RecipeCategory("Fish"),
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/6/6a/Surstr%C3%B6mming.jpg",
        steps = listOf(
            "Open the can of surströmming outdoors, as the smell is very strong",
            "Remove the herring from the can and rinse it under cold water",
            "Serve the herring with boiled potatoes, sour cream, chopped onion and flatbread",
            "Enjoy!"
        )
    ),
    Recipe(
        id = 8,
        title = "Sichuang Hotpot",
        description = "Sichuang Hotpot is a traditional Chinese dish made with spicy broth, meat, vegetables and noodles.",
        category = RecipeCategory("Chinese"),
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/6/63/Chongqing_hotpot_2.jpg",
        steps = listOf(
            "Prepare the broth by boiling water with Sichuang peppercorns, dried chilies, ginger, garlic, star anise, cinnamon and bay leaves",
            "Add the meat, vegetables and noodles to the broth and cook until tender",
            "Serve the hotpot with dipping sauces and enjoy!"
        )
    ),
    Recipe(
        id = 9,
        title = "Tiramisu",
        description = "Tiramisu is a classic Italian dessert made with coffee, mascarpone cheese, eggs, sugar and ladyfingers.",
        category = RecipeCategory("Dessert"),
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/5/58/Tiramisu_-_Raffaele_Diomede.jpg",
        steps = listOf(
            "Brew the coffee and let it cool",
            "Whisk the egg yolks and sugar together until pale and creamy",
            "Add the mascarpone cheese and mix until smooth",
            "Whisk the egg whites until stiff peaks form",
            "Fold the egg whites into the mascarpone mixture",
            "Dip the ladyfingers in the coffee and layer them in a dish",
            "Spread the mascarpone mixture over the ladyfingers",
            "Repeat the layers and refrigerate for at least 4 hours",
            "Dust with cocoa powder before serving"
        )
    ),
    Recipe(
        id = 10,
        title = "Borscht",
        description = "Borscht is a traditional Russian soup made with beets, cabbage, potatoes and beef.",
        category = RecipeCategory("Soup"),
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/a/a7/Borscht_served.jpg",
        steps = listOf(
            "Heat the oil in a large pan over medium heat.",
            "Add the onion, garlic, carrots and celery and cook until softened.",
            "Add the beef and cook until browned.",
            "Add the beets, cabbage, potatoes, tomatoes, bay leaves, salt and pepper and bring to a simmer.",
            "Stir in the stock and bring to a boil.",
            "Reduce the heat and simmer for 1 hour.",
            "Season with salt and pepper and serve with sour cream and rye bread."
        )
    ),
    Recipe(
        id = 11,
        title = "Peking Duck",
        description = "Peking Duck is a traditional Chinese dish made with roasted duck, pancakes, hoisin sauce and cucumber.",
        category = RecipeCategory("Chinese"),
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/8/81/Peking_Duck%2C_2014_%2802%29.jpg",
        steps = listOf(
            "Prepare the duck by removing the giblets and excess fat",
            "Rub the duck with a mixture of honey, soy sauce, five-spice powder and salt",
            "Hang the duck to dry for 24 hours",
            "Roast the duck in a hot oven until the skin is crispy and the meat is tender",
            "Serve the duck with pancakes, hoisin sauce, cucumber and spring onions"
        )
    ),
    Recipe(
        id = 12,
        title = "Sushi",
        description = "Sushi is a traditional Japanese dish made with vinegared rice, fish, vegetables and seaweed.",
        category = RecipeCategory("Japanese"),
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/6/60/Sushi_platter.jpg",
        steps = listOf(
            "Cook the rice and season with rice vinegar, sugar and salt",
            "Prepare the fish and vegetables by slicing them into thin strips",
            "Lay out a sheet of seaweed and spread a thin layer of rice over it",
            "Place the fish and vegetables on top of the rice",
            "Roll up the sushi using a bamboo mat",
            "Slice the sushi into bite-sized pieces and serve with soy sauce, wasabi and pickled ginger"
        )
    )
)