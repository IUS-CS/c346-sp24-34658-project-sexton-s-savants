package com.quark.client.cryptography

/**
 * This class serves as an interface for all crypto methods used
 * within the app.
 *
 * A CryptographicMethod object will be generated for each conversation
 * that the user has.
 */

interface CryptographicMethod {

    /**
     * The user's key pair. In the constructor, each subclass should
     * check the Keystore for the keys. If they exist, then pull from
     * the Keystore and assign to the data fields - else, generate a new
     * key pair.
     *
     * The constructor must also grab the chat partner's public key from
     * Firebase.
     *
     * Consider changing to an abstract class later, as checking the
     * Keystore will be shared behavior between classes.
     */
    val userPrivateKey: String
    val userPublicKey: String
    val partnerPublicKey: String

    /**
     * Encrypts the user's message using the partner's public key,
     * then returns the resulting string.
     * @param message: User's plaintext message to be sent
     * @return Encrypted message
     */
    fun encrypt(message: String): String

    /**
     * Decrypts the received message from partner using the
     * user's private key, then returns the plaintext message string.
     * @param message: Encrypted message received
     * @return Plaintext message
     */
    fun decrypt(message: String): String

    /**
     * Generates keypair if the user does not already have one
     * @return <User's Private Key, User's Public Key>
     */
    fun generateKeyPair(): Pair<String, String>

    /**
     * If the user already has a generated keypair, retrieves it
     * from the Android Keystore
     * @return <User's Private Key, User's Public Key>
     */
    fun getFromKeystore(): Pair<String, String>

    /**
     * Gets partner's public key from Firebase
     * @return Partner's Public Key
     */
    fun getPartnerPublicKey(): String


}