package app.iria.extractors

import app.iria.utils.temporal.OffsetDateTimes

import java.time.OffsetDateTime

case class RawInput( name : String,
                     content : Array[ Byte ],
                     contentType : Option[ String ] = None,
                     timestamp : OffsetDateTime = OffsetDateTimes.now )
