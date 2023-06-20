import json


class SetSerialiser(json.JSONEncoder):
    """
    a custom serialiser for json.dumps since python can't serialise sets...
    """

    def default(self, o):
        return list(o) if isinstance(o, set) else json.JSONEncoder.default(self, o)


def jsonStringify(jsonObj: object) -> str:
    """python's default json stringifier is rubbish, it can't handle UTF-8 or sets"""
    return json.dumps(jsonObj, indent=4, ensure_ascii=False, cls=SetSerialiser)
