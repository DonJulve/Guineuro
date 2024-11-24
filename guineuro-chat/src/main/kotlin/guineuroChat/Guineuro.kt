package guineuroChat

import java.util.Random
import java.util.Scanner

class Guineuro {
    private var responses: Map<String, List<String>>
    private var keywords: Set<String>
    private val random = Random()

    init {
        val familyAnswers = listOf(
            "Cuéntame más sobre tu familia.",
            "¿Cómo te llevas con tu familia?",
            "¿Es importante tu familia para ti?",
            "¿Qué piensas sobre el apoyo de tu familia?"
        )
        val iamAnswers = listOf(
            "Siento saber que te sientes *.",
            "¿Cuánto tiempo llevas sintiéndote *?",
            "¿Crees que es normal sentirse *?",
            "¿Te gusta sentirte *?",
            "¿Qué te hace sentir *?"
        )
        val dreamAnswers = listOf(
            "¿Qué te sugiere ese sueño?",
            "¿Sueñas con frecuencia?",
            "¿Qué personas aparecen en tus sueños?",
            "¿Te inquietan tus sueños?",
            "¿Crees que tus sueños reflejan algo importante?"
        )
        val greetingsAnswers = listOf(
            "¡Hola! ¿Cómo estás?",
            "¡Hola! ¿Qué te trae por aquí?",
            "¡Hola! ¿Qué tal estás?"
        )
        val needsAnswers = listOf(
            "¿Por qué tienes *?",
            "¿Qué puedes hacer para satisfacer esa necesidad?",
            "¿Hace cuánto tiempo tienes *?",
            "Cuéntame más sobre cómo te afecta tener *."
        )

        responses = mapOf(
            NOT_FOUND to listOf(
                "¿Qué te sugiere eso?",
                "Entiendo.",
                "No estoy seguro de entender completamente. ¿Puedes explicarme más?",
                "Eso es interesante.",
                "¿Por qué crees que es así?"
            ),
            // Respuestas a saludos
            "hola" to greetingsAnswers,
            "buenos días" to greetingsAnswers,
            "buenas tardes" to greetingsAnswers,
            "buenas noches" to greetingsAnswers,
            "ey" to listOf("¡Hey! ¿Cómo te va?"),
            // Respuestas a necesidades básicas
            "tener sueño" to needsAnswers.map { it.replace("*", "sueño") },
            "tener hambre" to needsAnswers.map { it.replace("*", "hambre") },
            "tener sed" to needsAnswers.map { it.replace("*", "sed") },
            "tener frío" to needsAnswers.map { it.replace("*", "frío") },
            "tener calor" to needsAnswers.map { it.replace("*", "calor") },
            "tener miedo" to needsAnswers.map { it.replace("*", "miedo") },
            // Casos adicionales
            "siempre" to listOf("¿Puedes pensar en un ejemplo específico?"),
            "porque" to listOf("¿Es esa la verdadera razón?"),
            "lo siento" to listOf("No necesitas disculparte."),
            "quizás" to listOf("No pareces muy seguro."),
            "creo" to listOf("¿Realmente lo crees?"),
            "tú" to listOf("Hablemos de ti, no de mí."),
            "sí" to listOf("¿Por qué crees que sí?", "Pareces muy seguro."),
            "no" to listOf("¿Por qué no?", "¿Estás seguro?"),
            "soy" to iamAnswers,
            "estoy" to iamAnswers,
            "me siento" to iamAnswers,
            "familia" to familyAnswers,
            "madre" to familyAnswers,
            "padre" to familyAnswers,
            "mamá" to familyAnswers,
            "papá" to familyAnswers,
            "hermana" to familyAnswers,
            "hermano" to familyAnswers,
            "esposo" to familyAnswers,
            "esposa" to familyAnswers,
            "sueño" to dreamAnswers,
            "pesadilla" to dreamAnswers,           
            "estrés" to listOf(
                "El estrés puede ser difícil. ¿Qué lo causa?",
                "¿Qué haces para relajarte cuando estás estresado?",
                "¿Crees que puedes reducir tu nivel de estrés?",
                "¿Cómo afecta el estrés a tu vida diaria?"
            ),
            "trabajo" to listOf(
                "Cuéntame sobre tu trabajo. ¿Disfrutas de lo que haces?",
                "¿Tu trabajo te hace sentir realizado?",
                "¿Sientes que tu trabajo afecta tu bienestar?",
                "¿Qué mejorarías en tu trabajo?"
            )
        )
        keywords = responses.keys
    }

    fun respond(s: Scanner): String {
        // Convertir todo el input a minúsculas para evitar sensibilidad a mayúsculas
        val input = s.nextLine().lowercase()

        // Buscar la palabra clave en cualquier parte del mensaje
        val keyword = keywords.firstOrNull { keyword ->
            input.contains(keyword)
        } ?: NOT_FOUND

        // Obtener una respuesta de la lista asociada a la palabra clave
        val responseList = responses.getValue(keyword)
        val response = responseList[random.nextInt(responseList.size)]

        // Reemplazar el carácter '*' si es necesario
        val hasSpread = response.indexOf('*') != -1
        return if (hasSpread) {
            val replacement = input.replace(keyword, "").trim()
            response.replaceFirst("*", replacement).trim()
        } else {
            response
        }
    }

    companion object {
        const val NOT_FOUND = "NOTFOUND"
    }
}
