from flask import Flask, request, jsonify
from gensim.models import KeyedVectors
import difflib

app = Flask(__name__)

# Path to the pre-trained Word2Vec model
model_path = "modele.bin"

# Load the Word2Vec model
word2vec_model = KeyedVectors.load_word2vec_format(model_path, binary=True)

@app.route("/get_similar_words", methods=["GET"])
def get_similar_words():
    """
    Endpoint to get similar words.
    
    @param request.args.get("mot_recherche", ""): The word to search.
    @param request.args.get("topn", 1): The number of similar words to return.
    @return str: A string with similar words.
    @throws KeyError: If the provided word is not in the Word2Vec model.
    @author: Rachid Ouahsouni.
    """
    
    # Get the search word and the number of similar words to return from the GET request parameters
    search_word = request.args.get("mot_recherche", "")
    topn = int(request.args.get("topn", 10))

    # Find similar words based on the provided word and the specified topn
    similar_words_result = find_similar_words(search_word, topn)

    # Return the response as a string
    return similar_words_result

def find_similar_words(word, topn=10):
    """
    Find similar words using the Word2Vec model.
    
    @param word: The word to search.
    @param topn: The number of similar words to return.
    @return str: A string with similar words.
    @throws KeyError: If the provided word is not in the Word2Vec model.
    @author: Rachid Ouahsouni.
    """
    
    try:
        # Attempt to find the most similar words using the Word2Vec model
        similar_words = word2vec_model.most_similar(word, topn=topn)
        
        # Create a list of words without their similarity scores
        words_without_similarity = [entry[0] for entry in similar_words]

        # Filter similar words based on the similarity with the original word
        filtered_words = [current_word for current_word in words_without_similarity if difflib.SequenceMatcher(None, current_word, word).ratio() <= 0.4]

        # Get the first word in the list, or "N/A" if the list is empty
        word_at_index_0 = filtered_words[0] if filtered_words else "N/A"

        # Update result_string with filtered words
        result_string = ', '.join(filtered_words)
        
        # Return the result string and the first similar word
        return f"Similar word: {word_at_index_0}"
    
    except KeyError:
        # Handle the case where the provided word is not in the Word2Vec model
        return ""

# Verify if the Python script is already run
if __name__ == "__main__":
    # Run the Flask app on host 0.0.0.0 and port 5000
    app.run(host="0.0.0.0", port=5000)
