Encryption / Decryption Helper Tool
====================================
Uses jasypt to encrypt and decrypt values.

To encrypt a value, POST to /encrypt with a body like this:

{
"secret":"old",
"value":"value to encrypt"
}

To decrypt a value, POST to /encrypt with a body like this:

{
"secret":"old",
"value":"ENC(XXX)"
}

To encrypt an old value with a new key, POST to /encryptswap with a body like this:

{
"oldsecret":"old",
"oldvalue":"ENC(XXX)",
"newsecret":"new"
}