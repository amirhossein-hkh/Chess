class Player:

    """Player class in represent a player in chess game

    Attributes :
        name (str): the name of the player
        color (Color): the color of player's chess pieces
    """

    def __init__(self, name, color):
        self.name = name
        self.color = color
        self.check = False

    def get_start_input(self):
        print("{}'s turn".format(self.name))
        start_row, start_col = [int(x) for x in input('choose the piece position (separated with space) :').split()]
        return start_row, start_col

    def get_move_input(self):
        end_row, end_col = [int(x) for x in input('choose the move (separated with space) :').split()]
        return end_row, end_col
