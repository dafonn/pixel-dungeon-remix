--
-- User: mike
-- Date: 23.11.2017
-- Time: 21:00
-- This file is part of Remixed Pixel Dungeon.
--

local RPD = require "scripts/lib/commonClasses"

local quest = require"scripts/lib/quest"

local mob = {}

mob.__index = mob

mob.init = function(desc)
    local ret = {}

    for k,v in pairs(desc) do
        ret[k] = v
    end

    setmetatable(ret, mob)

    return ret
end

mob.onDie = function(self,mob,cause)
    quest.mobDied(mob, cause)
    return self.die and self.die(mob, cause)
end

mob.onInteract = function(self,mob,chr)
    print(self, mob, chr)
    return self.interact and self.interact(mob, chr)
end

return mob
