class Network {
    constructor(nodeType) {

        const windowWidth = window.innerWidth;
        const offcenter = windowWidth < 601 ? 1 : 8 / 12; // col s8
        this._width = windowWidth * offcenter;
        this._margin = {'top': 20, 'right': 0, 'bottom': 0, 'left': 50}

        this._height = window.innerHeight - 64 - 40; // - header - margin(svg's margin-top + div.row's margin-bottom)

        this.offcenter =  windowWidth < 601 ? 1 : 8 / 12;
        this._nodeType = nodeType; // col s8

    }


    get margin() {
        return this._margin;
    }

    get nodeType() {
        return this._nodeType;
    }

    get width() {
        return this._width;
    }

    get height() {
        return this._height;
    }
}