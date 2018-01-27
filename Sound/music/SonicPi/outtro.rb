one = 0.4

define :riff do
  play_pattern_timed [:a4, :a4, :f4, :d4, :d5, :d5, :b4, :g4, :b4, :a4, :d4,:d4, :c4,:d4],[one, one*1.5,one*0.5,one,one,one*1.5,one*0.5,one,one,one,one,one,one,one*3], release: 0.4
end



define :drums do
  in_thread do
    4.times do
      sleep one
      sample :drum_heavy_kick
      sleep one
      sample :drum_snare_hard
      sleep one
      sample :drum_heavy_kick
      sleep one/2
      sample :drum_heavy_kick
      sleep one/2
      sample :drum_snare_hard
    end
  end
end

define :drums2 do
  in_thread do
    4.times do
      sleep one
      sample :drum_heavy_soft
      sleep one
      sample :drum_snare_soft
    end
  end
end

define :hihat do
  in_thread do
    4.times do
      sleep one
      sample :drum_cymbal_closed
      sleep one
      sample :drum_cymbal_pedal
    end
  end
end


define :bg do
  in_thread do
    4.times do
      sample :ambi_glass_rub, rate:0.5, attack: 0.3, release: 0.2, amp: 0.1
    end
  end
end


define :boom do
  in_thread do
    4.times do
      sleep one
      sample :bd_ada
      sample :bd_haus
      sleep one*2
      sample :bd_fat
      sleep one
    end
  end
end



define :boom2 do
  in_thread do
    with_fx :reverb , room: 0.1 do
      with_synth :dsaw do
        tone = 45
        2.times do
          4.times do
            sleep one
            play tone, release:0.15 , pan:-1, amp: 0.4
            play tone-12, release:0.15 , pan:1, amp: 0.4
            sleep one*0.01
            play tone, release:0.15 , pan:-1, amp: 0.4
            play tone-12, release:0.15 , pan:1, amp: 0.4
            sleep one*0.99
          end
          tone = 45
        end
        
      end
    end
  end
end

define :hi do
  in_thread do
    4.times do
      sleep one
      sample :drum_cymbal_soft, release: 0.05, amp: 0.2
      sleep one/2
      sample :drum_cymbal_soft, release: 0.05, amp: 0.2
      sleep 1.5*one
    end
  end
end

use_synth :piano
with_fx :reverb do
  riff
  riff
end



with_fx :distortion do
  
  use_synth :fm
  2.times do
    drums2
    riff
  end
end


with_fx :distortion, distort: 0.5 do
  
  use_synth :fm
  4.times do
    boom2
    #drums2
    #hihat
    riff
    one = one*0.9
    tone = tone -1
  end
end


