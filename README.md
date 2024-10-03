To encrypt a value, POST to /encrypt with a body like this:

{
"secret":"old",
"value":"value to encrypt"
}

To encrypt an old value with a new key, POST to /encryptswap with a body like this:

{
"oldsecret":"old",
"oldvalue":"ENC(XXX)",
"newsecret":"new"
}