package app.iria.extractors.factiva

import com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonProperty}

import scala.beans.BeanProperty

@JsonIgnoreProperties( ignoreUnknown = true )
case class Factiva( @BeanProperty @JsonProperty( "an" ) an : String = null,
                    @BeanProperty @JsonProperty( "ingestion_datetime" ) ingestionDatetime : String = null,
                    @BeanProperty @JsonProperty( "publication_date" ) publicationDate : String = null,
                    @BeanProperty @JsonProperty( "publication_datetime" ) publicationDatetime : String = null,
                    @BeanProperty @JsonProperty( "snippet" ) snippet : String = null,
                    @BeanProperty @JsonProperty( "body" ) body : String = null,
                    @BeanProperty @JsonProperty( "art" ) art : String = null,
                    @BeanProperty @JsonProperty( "action" ) action : String = null,
                    @BeanProperty @JsonProperty( "credit" ) credit : String = null,
                    @BeanProperty @JsonProperty( "byline" ) byline : String = null,
                    @BeanProperty @JsonProperty( "document_type" ) documentType : String = null,
                    @BeanProperty @JsonProperty( "language_code" ) languageCode : String = null,
                    @BeanProperty @JsonProperty( "title" ) title : String = null,
                    @BeanProperty @JsonProperty( "copyright" ) copyright : String = null,
                    @BeanProperty @JsonProperty( "dateline" ) dateline : String = null,
                    @BeanProperty @JsonProperty( "source_code" ) sourceCode : String = null,
                    @BeanProperty @JsonProperty( "modification_datetime" ) modificationDateTime : String = null,
                    @BeanProperty @JsonProperty( "modification_date" ) modificationDate : String = null,
                    @BeanProperty @JsonProperty( "section" ) section : String = null,
                    @BeanProperty @JsonProperty( "company_codes" ) companyCodes : String = null,
                    @BeanProperty @JsonProperty( "publisher_name" ) publisherName : String = null,
                    @BeanProperty @JsonProperty( "region_of_origin" ) regionOfOrigin : String = null,
                    @BeanProperty @JsonProperty( "word_count" ) word_count : String = null,
                    @BeanProperty @JsonProperty( "subject_codes" ) subjectCodes : String = null,
                    @BeanProperty @JsonProperty( "region_codes" ) regionCodes : String = null,
                    @BeanProperty @JsonProperty( "industry_codes" ) industryCodes : String = null,
                    @BeanProperty @JsonProperty( "person_codes" ) personCodes : String = null,
                    @BeanProperty @JsonProperty( "currency_codes" ) currencyCodes : String = null,
                    @BeanProperty @JsonProperty( "market_index_codes" ) marketIndexCodes : String = null,
                    @BeanProperty @JsonProperty( "company_codes_about" ) companyCodesAbout : String = null,
                    @BeanProperty @JsonProperty( "company_codes_association" ) companyCodesAssociation : String = null,
                    @BeanProperty @JsonProperty( "company_codes_lineage" ) companyCodesLineage : String = null,
                    @BeanProperty @JsonProperty( "company_codes_occur" ) companyCodesOccur : String = null,
                    @BeanProperty @JsonProperty( "company_codes_relevance" ) companyCodesRelevance : String = null,
                    @BeanProperty @JsonProperty( "source_name" ) sourceName : String = null )
