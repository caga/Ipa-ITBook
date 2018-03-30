/**
 * ShakespeareSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.xmlme.WebServices;

public interface ShakespeareSoap extends java.rmi.Remote {

    // <h3>GetSpeech requires a string formatted phrase from one of Shakespeare's
    // plays as input. The speech, speaker, and play will be returned as
    // an XML string. <br /><br />Sample Shakespeare Phrases: <br /><br />To
    // be, or not to be<br />My kingdom for a horse<br />Get thee to a nunnery<br
    // />There are more things in heaven and earth<br />I will wear my heart
    // upon my sleeve<br />When beggars die, there are no comets seen<br
    // />Lord, what fools these mortals be<br />Et tu, Brute<br />Sound and
    // fury<br />Friends, romans, countrymen<br />Something wicked this way
    // comes<br />To sleep: perchance to dream<br />Green-eyed monster<br
    // />This was the noblest Roman of them all<br />Oft expectation fails
    // and most oft there Where most it promises<br />Eye of newt and toe
    // of frog<br />Frailty, thy name is woman<br />What light through yonder
    // window breaks<br />Think you I am no stronger than my sex<br />Cowards
    // die many times before their deaths<br />How poor are they that have
    // not patience<br />Quality of mercy<br />My words fly up, my thoughts
    // remain below<br />Bell, book, and candle<br />Something is rotten
    // in the state of Denmark<br />Beware the ides of March<br />A stage
    // where every man must play a part<br />Though this be madness, yet
    // there is method in 't<br />Is this a dagger which I see before me<br
    // />Now go we in content To liberty and not to banishment<br />Band
    // of brothers<br />Alas, poor Yorick! I knew him<br />The world's mine
    // oyster<br />Nothing will come of nothing<br />That man that hath a
    // tongue<br />All the world's a stage<br />The course of true love never
    // did run smooth<br />Love looks not with the eyes, but with the mind<br
    // />Let every eye negotiate for itself<br />Kiss me, Kate<br />The play
    // 's the thing<br />I am constant as the northern star<br />The man
    // that hath no music in himself<br />We are such stuff As dreams are
    // made on<br />This was the most unkindest cut of all<br />Journeys
    // end in lovers meeting<br />Yond Cassius has a lean and hungry look<br
    // />My only love sprung from my only hate<br />I am fortune's fool<br
    // />Loved not wisely but too well<br />O coward conscience, how dost
    // thou afflict me<br />When shall we three meet again<br />A plague
    // o' both your houses<br />Out, damned spot<br />To-morrow, and to-morrow,
    // and to-morrow<br />Our remedies oft in ourselves do lie<br />Not that
    // I loved Caesar less<br />Winter of our discontent<br />Parting is
    // such sweet sorrow<br />O Romeo, Romeo! wherefore art thou Romeo<br
    // />The lady protests too much, methinks<br />What a piece of work is
    // a man<br />The fault, dear Brutus, is not in our stars<br />All that
    // glitters is not gold<br />What's in a name<br />Thus with a kiss I
    // die</h3>
    public java.lang.String getSpeech(java.lang.String request) throws java.rmi.RemoteException;
}
