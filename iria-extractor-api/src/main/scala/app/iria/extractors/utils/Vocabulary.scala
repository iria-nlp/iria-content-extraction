package app.iria.extractors.utils

import better.files.{File, Resource}

object Vocabulary {

    private val DEFAULT_ENGLISH_VOCAB : String = "vocabularies/english/english-us-ca-au-gb-final.txt"

    def default( ) : Vocabulary = {
        create( DEFAULT_ENGLISH_VOCAB )
    }

    def create( path : String ) : Vocabulary = {
        val words : Set[ String ] = {
            if ( path.startsWith( "/" ) ) File( path ).contentAsString.lines.toSet
            else Resource.getAsString( path ).lines.toSet
        }
        new Vocabulary( words )
    }
}

class Vocabulary( words : Set[ String ] ) {
    def contains( token : String ) : Boolean = {
        words.contains( token )
    }
}
